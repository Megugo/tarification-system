package IntegrationTests.api_tests;



import IntegrationTests.api_tests.db.PosgresConnector;
import IntegrationTests.api_tests.dto.clientdto.AbonentRegisterDto;
import IntegrationTests.api_tests.dto.clientdto.ReportDto;
import IntegrationTests.api_tests.endpoints.ApiAbonentRegisterEndpoint;
import IntegrationTests.api_tests.endpoints.ApiAbonentReportEndpoint;
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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;

@DisplayName("/abonent/report/{number}")
@ExtendWith(ApiTestExtension.class)
public class ApiAbonentReportTest {
    ReportDto reportDTO;
    static AbonentRegisterDto abonentRegisterDto;

    public static Stream<Arguments> unsuccessfulGetUserReport() {
        return Stream.of(Arguments.of(401,
                        abonentRegisterDto.getPhoneNumber(),
                        abonentRegisterDto.getPassword()+"111",
                        abonentRegisterDto.getPhoneNumber()),
                Arguments.of(403,
                        abonentRegisterDto.getPhoneNumber(),
                        abonentRegisterDto.getPassword(),
                        abonentRegisterDto.getPhoneNumber()+"0"));
    }

    @BeforeAll
    static void createUser(){
        //берем гарантированно сеществующий номер, который после запроса заносится в БД BRT и имеет историю звонков
        String phoneNumber = given()
                .get("http://localhost:8081/prepare_cdr")
                .jsonPath()
                .getString("numbers[0].phoneNumber");
        //ToDo добавить проверки на номер с префиксом "+"


        abonentRegisterDto = AbonentRegisterDto.builder()
                .phoneNumber(phoneNumber)
                .password(new Faker().internet().password())
                .build();

        new ApiAbonentRegisterEndpoint().newAbonentRegistration(abonentRegisterDto);
    }

    @AfterAll
    static void clearUsers() throws SQLException {
        PosgresConnector posgresConnector = new PosgresConnector();
        posgresConnector.clearAllTables();
    }


    @Test
    @DisplayName("GET ../abonent/report/: 200, получение детализации звонков авторизованныи пользователем")
    void successGetReportTest(){

        reportDTO = new ApiAbonentReportEndpoint().getRepot(abonentRegisterDto);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(reportDTO.getPhoneNumber()).isEqualTo(abonentRegisterDto.getPhoneNumber());
        softAssertions.assertThat(reportDTO.getTariffIndex()).isNotEmpty();
        softAssertions.assertThat(reportDTO.getMonetaryUnit()).isNotEmpty();
        softAssertions.assertAll();
    }

    @ParameterizedTest(name = "{0} ожидаемый статус код")
    @DisplayName("GET ../abonent/report/: неудачное получение детализации")
    @MethodSource("unsuccessfulGetUserReport")
    void unsuccessfulAuthUserGetReportTest(int expectedStatusCode, String phoneNumber,  String password,String queryNumber){
        given()
                .auth()
                .preemptive()
                .basic(phoneNumber, password)
                .get(new ApiAbonentReportEndpoint().getEndpoint()+"/"+queryNumber)
                .then()
                .statusCode(expectedStatusCode);
    }
}
