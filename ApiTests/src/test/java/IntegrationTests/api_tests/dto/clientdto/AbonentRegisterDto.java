package IntegrationTests.api_tests.dto.clientdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbonentRegisterDto{

	@JsonProperty("password")
	private String password;

	@JsonProperty("phoneNumber")
	private String phoneNumber;
}