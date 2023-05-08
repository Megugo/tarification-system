package IntegrationTests.api_tests.endpoints;



import IntegrationTests.api_tests.dto.clientdto.AbonentRegisterDto;
import IntegrationTests.api_tests.dto.clientdto.ReportDto;

import static io.restassured.RestAssured.given;

@Endpoint("/abonent/report")
public class ApiAbonentReportEndpoint extends BaseEndpoint{

    public ReportDto getRepot(AbonentRegisterDto abonentRegisterDto){

        return given()
                .auth()
                .preemptive()
                .basic(abonentRegisterDto.getPhoneNumber(), abonentRegisterDto.getPassword())
                .get(getEndpoint()+"/"+abonentRegisterDto.getPhoneNumber())
                .then()
                .statusCode(200)
                .extract()
                .as(ReportDto.class);
    }
}
