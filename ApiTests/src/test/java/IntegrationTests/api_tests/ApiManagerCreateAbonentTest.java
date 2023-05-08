package IntegrationTests.api_tests;

import IntegrationTests.api_tests.DataGenerator.DataGenerator;
import IntegrationTests.api_tests.db.PosgresConnector;
import IntegrationTests.api_tests.db.Tariff;
import IntegrationTests.api_tests.dto.clientdto.PayDTO;
import IntegrationTests.api_tests.dto.managerdto.ManagedAbonentDto;
import IntegrationTests.api_tests.dto.managerdto.ManagerRegisterDto;
import IntegrationTests.api_tests.endpoints.ApiAbonentPayEndpoint;
import IntegrationTests.api_tests.endpoints.ApiAbonentReportEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerAbonentEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerRegisterEndpoint;
import IntegrationTests.api_tests.extentsion.ApiTestExtension;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("/manager/abonent")
@ExtendWith(ApiTestExtension.class)
public class ApiManagerCreateAbonentTest {

    static ManagerRegisterDto managerRegisterDto;
    static List<Tariff> tariffIDs;

    static {
        try {
            tariffIDs = new PosgresConnector().getTariffIDs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void createManager(){
        //параметры менеджера
        Faker faker = new Faker();
        managerRegisterDto = ManagerRegisterDto.builder()
                .login(faker.name().username())
                .password(faker.internet().password())
                .build();
        //регистрация менеджера через API (т.к. используется хещирование пароля)
        new ApiManagerRegisterEndpoint().newManagerRegistration(managerRegisterDto);
    }
    @AfterAll
    static void clearUsers() throws SQLException {
        PosgresConnector posgresConnector = new PosgresConnector();
        posgresConnector.clearAllTables();
    }

    public static Stream<ManagedAbonentDto> successfulAbonentCreation() {
        DataGenerator dataGenerator = new DataGenerator();
        return Stream.of(
                //Валидный номер длиной 11 (нижняя граница)
                ManagedAbonentDto.builder()
                        .phoneNumber(dataGenerator.generatePhoneNumber(11))
                        .tariffId(tariffIDs.get((int) (Math.random() * tariffIDs.size())).getId())
                        .balance(0)
                        .build(),
                //Валидный номер длиной 13 (условная верхняя граница) с отрицательным балансом
                ManagedAbonentDto.builder()
                        .phoneNumber(dataGenerator.generatePhoneNumber(13))
                        .tariffId(tariffIDs.get((int) (Math.random() * tariffIDs.size())).getId())
                        .balance(0)
                        .build()
        );
    }

    @ParameterizedTest
    @DisplayName("POST ../manager/abonent: 200, создание валидных абонентов")
    @MethodSource("successfulAbonentCreation")
    void successAbonentCreationTest(ManagedAbonentDto expectedManagedAbonentDto){

        ManagedAbonentDto managedAbonentDto = new ApiManagerAbonentEndpoint()
                .newAbonentCreation(managerRegisterDto, expectedManagedAbonentDto);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(expectedManagedAbonentDto.getPhoneNumber()).isEqualTo(managedAbonentDto.getPhoneNumber());
        softAssertions.assertThat(expectedManagedAbonentDto.getTariffId()).isEqualTo(managedAbonentDto.getTariffId());
        softAssertions.assertThat(expectedManagedAbonentDto.getBalance()).isEqualTo(managedAbonentDto.getBalance());

        softAssertions.assertAll();
    }

    public static Stream<Arguments> unsuccessfulAbonentCreation() {
        DataGenerator dataGenerator = new DataGenerator();
        return Stream.of(
                //Невалидный номер длиной 10 (меньше нижней границы)
                Arguments.of(403,
                ManagedAbonentDto.builder()
                        .phoneNumber(dataGenerator.generatePhoneNumber(10))
                        .tariffId(tariffIDs.get((int) (Math.random() * tariffIDs.size())).getId())
                        .balance(0)
                        .build()),
                //Невалидный номер длиной 14 (выше верхней границы)
                Arguments.of(403,
                ManagedAbonentDto.builder()
                        .phoneNumber(dataGenerator.generatePhoneNumber(14))
                        .tariffId(tariffIDs.get((int) (Math.random() * tariffIDs.size())).getId())
                        .balance(0)
                        .build()),
                //Случайная строка вместо тарифа
                Arguments.of(403,
                ManagedAbonentDto.builder()
                        .phoneNumber(dataGenerator.generatePhoneNumber(11))
                        .tariffId(dataGenerator.randomString(2))
                        .balance(0)
                        .build()),
                Arguments.of(403,
                ManagedAbonentDto.builder()
                        .phoneNumber(dataGenerator.generatePhoneNumber(11)+".1")
                        .tariffId(tariffIDs.get((int) (Math.random() * tariffIDs.size())).getId())
                        .balance(0)
                        .build())

        );

    }

    @ParameterizedTest(name = "{1} параметры запроса,{0} код ошибки ")
    @DisplayName("POST ../manager/abonent, создание невалидных абонентов")
    @MethodSource("unsuccessfulAbonentCreation")
    void unsuccessAbonentCreationTest(int expectedStatusCode, ManagedAbonentDto notValidManagedAbonentDto){

        given()
                .auth()
                .preemptive()
                .basic(managerRegisterDto.getLogin(), managerRegisterDto.getPassword())
                .body(notValidManagedAbonentDto)
                .post(new ApiManagerAbonentEndpoint().getEndpoint())
                .then()
                .statusCode(expectedStatusCode);
    }

    @Test
    @DisplayName("POST ../manager/abonent: 403, создание абонента с занятым номером")
    void existedAbonentCreationTest(){
        ManagedAbonentDto managedAbonentDto = successfulAbonentCreation().findFirst().orElseThrow();
        new ApiManagerAbonentEndpoint()
                .newAbonentCreation(managerRegisterDto, managedAbonentDto);

        given()
                .auth()
                .preemptive()
                .basic(managerRegisterDto.getLogin(), managerRegisterDto.getPassword())
                .body(managedAbonentDto)
                .post(new ApiManagerAbonentEndpoint().getEndpoint())
                .then()
                .statusCode(403);
    }



}
