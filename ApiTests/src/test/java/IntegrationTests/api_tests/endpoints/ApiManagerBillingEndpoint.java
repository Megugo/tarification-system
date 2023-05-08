package IntegrationTests.api_tests.endpoints;

import IntegrationTests.api_tests.dto.managerdto.BillingResultDto;
import IntegrationTests.api_tests.dto.managerdto.ManagedAbonentDto;
import IntegrationTests.api_tests.dto.managerdto.ManagerRegisterDto;

import static io.restassured.RestAssured.given;

@Endpoint("/manager/billing")
public class ApiManagerBillingEndpoint extends BaseEndpoint{
    public BillingResultDto runBilling(ManagerRegisterDto managerCreds) {

        return given()
                .auth()
                .preemptive()
                .basic(managerCreds.getLogin(), managerCreds.getPassword())
                .body("{\"action\": \"string\"}")
                .patch(getEndpoint())
                .then()
                .statusCode(200)
                .extract()
                .as(BillingResultDto.class);
    }
}
