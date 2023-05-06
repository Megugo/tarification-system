package IntegrationTests.api_tests.dto.clientdto;

import java.util.List;

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
public class ReportResponse{

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("tariffIndex")
	private String tariffIndex;

	@JsonProperty("payload")
	private List<PayloadItem> payload;

	@JsonProperty("monetaryUnit")
	private String monetaryUnit;

	@JsonProperty("id")
	private int id;

	@JsonProperty("totalCost")
	private int totalCost;
}