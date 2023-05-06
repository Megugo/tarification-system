package IntegrationTests.api_tests.endpoints;

import lombok.Getter;

public class BaseEndpoint {
    @Getter
    String endpoint = this.getClass().getAnnotation(Endpoint.class).value();
}
