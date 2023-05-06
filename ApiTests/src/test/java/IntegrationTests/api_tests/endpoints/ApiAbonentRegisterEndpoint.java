package IntegrationTests.api_tests.endpoints;

import IntegrationTests.api_tests.dto.clientdto.AbonentRegisterDto;
import IntegrationTests.api_tests.dto.clientdto.ReportResponse;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/register")
public class ApiAbonentRegisterEndpoint extends BaseEndpoint{
    public AbonentRegisterDto newAbonentRegistration(String phoneNumber, String password){
        AbonentRegisterDto abonentDto = AbonentRegisterDto.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
        System.out.println(abonentDto.getPhoneNumber());
        given()
                .body(abonentDto)
                .post(getEndpoint())
                .then()
                .statusCode(200);

        return abonentDto;
    }
}
