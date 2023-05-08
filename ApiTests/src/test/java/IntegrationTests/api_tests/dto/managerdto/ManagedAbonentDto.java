package IntegrationTests.api_tests.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagedAbonentDto{

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("balance")
	private double balance;

	@JsonProperty("tariffId")
	private String tariffId;

	@JsonProperty("id")
	private String id;
}