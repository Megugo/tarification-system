package IntegrationTests.api_tests.dto.prepdto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CDRDto{

	@JsonProperty("numbers")
	private List<NumbersItem> numbers;
}