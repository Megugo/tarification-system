package IntegrationTests.api_tests.dto.prepdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumbersItem{

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("balance")
	private double balance;
}