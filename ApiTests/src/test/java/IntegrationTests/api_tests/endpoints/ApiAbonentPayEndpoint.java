package IntegrationTests.api_tests.endpoints;

import IntegrationTests.api_tests.dto.clientdto.AbonentRegisterDto;
import IntegrationTests.api_tests.dto.clientdto.PayDTO;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/pay")
public class ApiAbonentPayEndpoint extends BaseEndpoint {
    public PayDTO payByPhone(PayDTO payDTO, AbonentRegisterDto abonentRegisterDto) {

        return given()
                .auth()
                .preemptive()
                .basic(abonentRegisterDto.getPhoneNumber(), abonentRegisterDto.getPassword())
                .body(payDTO)
                .patch(getEndpoint())
                .then()
                .statusCode(200)
                .extract()
                .as(PayDTO.class);
    }
}
