package IntegrationTests.api_tests;

import IntegrationTests.api_tests.db.PosgresConnector;
import IntegrationTests.api_tests.dto.clientdto.AbonentRegisterDto;
import IntegrationTests.api_tests.dto.clientdto.PayDTO;
import IntegrationTests.api_tests.dto.clientdto.ReportResponse;
import IntegrationTests.api_tests.endpoints.ApiAbonentPayEndpoint;
import IntegrationTests.api_tests.endpoints.ApiAbonentRegisterEndpoint;
import IntegrationTests.api_tests.endpoints.ApiAbonentReportEndpoint;
import IntegrationTests.api_tests.extentsion.ApiTestExtension;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
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

import static io.restassured.RestAssured.given;

@DisplayName("/abonent/pay")
@ExtendWith(ApiTestExtension.class)
public class ApiAbonentPayTest {
    static String brtPhoneNumber;
    static AbonentRegisterDto abonentRegisterDto;

    @BeforeAll
    static void createUser() throws SQLException {
//        берем гарантированно сеществующий номер, который после запроса заносится в БД BRT и имеет историю звонков
        int baseId = 1;
        String phoneNumber = new PosgresConnector().addBRTUserWitchRandomParams(11,baseId);



        abonentRegisterDto = AbonentRegisterDto.builder()
                .phoneNumber(phoneNumber)
                .password(new Faker().internet().password())
                .build();

        new ApiAbonentRegisterEndpoint().newAbonentRegistration( abonentRegisterDto.getPhoneNumber(), abonentRegisterDto.getPassword());
    }

    @AfterAll
    static void clearUsers() throws SQLException {
        PosgresConnector posgresConnector = new PosgresConnector();
        posgresConnector.clearAllTables();
    }

    public static Stream<PayDTO> successfulAbonentPay() throws SQLException {
        return Stream.of(
                PayDTO.builder()
                        .phoneNumber(abonentRegisterDto.getPhoneNumber())
                        .money(1)
                        .build(),
                PayDTO.builder()
                        .phoneNumber(new PosgresConnector().addBRTUserWitchRandomParams(11,2))
                        .money(400)
                        .build()
        );
    }

    public static Stream<Arguments> unsuccessfulAbonentPay() throws SQLException {
        return Stream.of(
                Arguments.of(401,
                        abonentRegisterDto.getPhoneNumber(),
                        abonentRegisterDto.getPassword()+"1",
                        1),
                Arguments.of(403,
                        abonentRegisterDto.getPhoneNumber(),
                        abonentRegisterDto.getPassword(),
                        0)
        );
    }


    @ParameterizedTest(name = "{0} параметры валидного запроса на оплату")
    @DisplayName("GET ../abonent/pay: 200, получение номера оплатившего")
    @MethodSource("successfulAbonentPay")
    void successAbonentPayTest(PayDTO payDto){

        PayDTO expectedPayDto = new ApiAbonentPayEndpoint().payByPhone(payDto,abonentRegisterDto.getPhoneNumber(),abonentRegisterDto.getPassword());
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(expectedPayDto.getPhoneNumber()).isEqualTo(payDto.getPhoneNumber());
        softAssertions.assertThat(expectedPayDto.getMoney()).isNotNull();
//        softAssertions.assertThat(payDto.getId()).isNotNull();

        softAssertions.assertAll();

    }

    @ParameterizedTest(name = "{0} код ошибки")
    @DisplayName("GET ../abonent/pay: 200, невалидные запросы")
    @MethodSource("unsuccessfulAbonentPay")
    void unsuccessfulAbonentPayTest(int expectedStatusCode, String phoneNumber,  String password, int balance){

        given()
                .auth()
                .preemptive()
                .basic(phoneNumber, password)
                .body(PayDTO.builder()
                        .phoneNumber(phoneNumber)
                        .money(balance)
                        .build())
                .patch(new ApiAbonentPayEndpoint().getEndpoint())
                .then()
                .statusCode(expectedStatusCode);
    }
}
