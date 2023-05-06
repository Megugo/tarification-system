package IntegrationTests.api_tests.endpoints;



import IntegrationTests.api_tests.dto.clientdto.ReportResponse;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/report")
public class ApiAbonentReportEndpoint extends BaseEndpoint{

    public ReportResponse getRepot(String phoneNumber, String login, String password){
        return given()
                .auth()
                .preemptive()
                .basic(login, password)
                .get(getEndpoint()+"/"+phoneNumber)
                .then()
                .statusCode(200)
                .extract()
                .as(ReportResponse.class);
    }
}
