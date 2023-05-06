package IntegrationTests.api_tests.endpoints;

import IntegrationTests.api_tests.dto.clientdto.PayDTO;
import IntegrationTests.api_tests.dto.clientdto.ReportResponse;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/pay")
public class ApiAbonentPayEndpoint extends BaseEndpoint {
    public PayDTO payByPhone(PayDTO payDTO, String login, String password) {
        return given()
                .auth()
                .preemptive()
                .basic(login, password)
                .body(payDTO)
                .patch(getEndpoint())
                .then()
                .statusCode(200)
                .extract()
                .as(PayDTO.class);
    }
}
