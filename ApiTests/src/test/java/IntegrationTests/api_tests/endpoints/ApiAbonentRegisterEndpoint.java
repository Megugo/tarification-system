package IntegrationTests.api_tests.endpoints;

import IntegrationTests.api_tests.dto.clientdto.AbonentRegisterDto;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/register")
public class ApiAbonentRegisterEndpoint extends BaseEndpoint{
    public void newAbonentRegistration(AbonentRegisterDto abonentDto){

        given()
                .body(abonentDto)
                .post(getEndpoint())
                .then()
                .statusCode(200);
    }
}
