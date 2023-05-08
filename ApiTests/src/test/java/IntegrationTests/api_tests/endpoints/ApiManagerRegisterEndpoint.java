package IntegrationTests.api_tests.endpoints;

import IntegrationTests.api_tests.dto.clientdto.AbonentRegisterDto;
import IntegrationTests.api_tests.dto.managerdto.ManagerRegisterDto;

import static io.restassured.RestAssured.given;

@Endpoint("/manager/register")
public class ApiManagerRegisterEndpoint extends BaseEndpoint{
    public void newManagerRegistration(ManagerRegisterDto managerDto) {

        given()
                .body(managerDto)
                .post(getEndpoint())
                .then()
                .statusCode(200);
    }
}
