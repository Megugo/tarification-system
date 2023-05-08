package IntegrationTests.api_tests;

import IntegrationTests.api_tests.db.PosgresConnector;
import IntegrationTests.api_tests.dto.managerdto.BillingResultDto;
import IntegrationTests.api_tests.dto.managerdto.ManagedAbonentDto;
import IntegrationTests.api_tests.dto.managerdto.ManagerRegisterDto;
import IntegrationTests.api_tests.dto.prepdto.CDRDto;
import IntegrationTests.api_tests.endpoints.ApiAbonentReportEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerAbonentEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerBillingEndpoint;
import IntegrationTests.api_tests.endpoints.ApiManagerRegisterEndpoint;
import IntegrationTests.api_tests.extentsion.ApiTestExtension;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;

@DisplayName("/manager/billing")
@ExtendWith(ApiTestExtension.class)
public class ApiManagerBillingTest {
    static ManagerRegisterDto managerRegisterDto;
    static CDRDto cdrDto;

    @BeforeAll
    static void createManager(){
        //генерация номеров для биллинга
        cdrDto = given()
                .get("http://localhost:8081/prepare_cdr")
                .then()
                .statusCode(200)
                .extract()
                .as(CDRDto.class);
        //параметры менеджера
        Faker faker = new Faker();
        managerRegisterDto = ManagerRegisterDto.builder()
                .login(faker.name().username())
                .password(faker.internet().password())
                .build();
        //регистрация менеджера через API (т.к. используется хещирование пароля)
        new ApiManagerRegisterEndpoint().newManagerRegistration(managerRegisterDto);
    }

    @AfterAll
    static void clearUsers() throws SQLException {
        PosgresConnector posgresConnector = new PosgresConnector();
        posgresConnector.clearAllTables();
    }

    @Test
    @DisplayName("POST ../manager/billing: 200, биллинг абонентов")
    void billingTest(){

        BillingResultDto billingResultDto = new ApiManagerBillingEndpoint().runBilling(managerRegisterDto);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(billingResultDto.getNumbers().size()).isEqualTo(cdrDto.getNumbers().size());
        softAssertions.assertAll();
    }
}
