package IntegrationTests.api_tests;

import IntegrationTests.api_tests.DataGenerator.DataGenerator;
import IntegrationTests.api_tests.db.PosgresConnector;
import IntegrationTests.api_tests.db.Tariff;
import IntegrationTests.api_tests.dto.managerdto.BillingResultDto;
import IntegrationTests.api_tests.dto.managerdto.ManagedAbonentDto;
import IntegrationTests.api_tests.dto.managerdto.ManagerRegisterDto;
import IntegrationTests.api_tests.endpoints.ApiManagerAbonentEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerBillingEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerChangeTariffEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerRegisterEndpoint;
import IntegrationTests.api_tests.extentsion.ApiTestExtension;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

@DisplayName("/manager/changeTariff")
@ExtendWith(ApiTestExtension.class)
public class ApiManagerChangeTariffTest {

    static ManagerRegisterDto managerRegisterDto;
    static ManagedAbonentDto managedAbonentDto;
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
        //создание пользователя для изменения
        managedAbonentDto = new ApiManagerAbonentEndpoint()
                .newAbonentCreation(managerRegisterDto, ManagedAbonentDto.builder()
                        .phoneNumber(new DataGenerator().generatePhoneNumber(11))
                        .tariffId(tariffIDs.get((int) (Math.random() * tariffIDs.size())).getId())
                        .balance(0)
                        .build());
    }



    @Test
    @DisplayName("PATCH ../manager/changeTariff: 200, изменение тарифа")
    void successfulBillingTest(){
        //arrange
        managedAbonentDto.setTariffId(tariffIDs.get((int) (Math.random() * tariffIDs.size())).getId());
        //act
        ManagedAbonentDto actualAbonentDto = new ApiManagerChangeTariffEndpoint()
                .changeTariff(managerRegisterDto, managedAbonentDto);
        //assert
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualAbonentDto.getPhoneNumber()).isEqualTo(managedAbonentDto.getPhoneNumber());
        softAssertions.assertThat(actualAbonentDto.getTariffId()).isEqualTo(managedAbonentDto.getTariffId());
        softAssertions.assertThat(actualAbonentDto.getBalance()).isEqualTo(managedAbonentDto.getBalance());
        softAssertions.assertThat(actualAbonentDto.getId()).isNotEmpty();

        softAssertions.assertAll();
    }

    public static Stream<Arguments> unsuccessfulBilling() {
        return Stream.of(
                Arguments.of(403, managedAbonentDto.getPhoneNumber(), "00"),
                Arguments.of(403, managedAbonentDto.getPhoneNumber(), "null"),
                Arguments.of(403, managedAbonentDto.getPhoneNumber(), "(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@")
        );

    }


    @ParameterizedTest
    @DisplayName("PATCH ../manager/changeTariff: 403, изменение на несуществующий  тарифа")
    @MethodSource("unsuccessfulBilling")
    void unsuccessfulBillingTest(int expectedStatusCode, String phoneNumber, String tariffId){

        given()
                .auth()
                .preemptive()
                .basic(managerRegisterDto.getLogin(), managerRegisterDto.getPassword())
                .body(ManagedAbonentDto.builder()
                        .phoneNumber(phoneNumber)
                        .tariffId(tariffId)
                        .build())
                .patch(new ApiManagerChangeTariffEndpoint().getEndpoint())
                .then()
                .statusCode(expectedStatusCode);
    }

}
