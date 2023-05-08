package IntegrationTests.api_tests.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRegisterDto{

	@JsonProperty("password")
	private String password;

	@JsonProperty("login")
	private String login;
}