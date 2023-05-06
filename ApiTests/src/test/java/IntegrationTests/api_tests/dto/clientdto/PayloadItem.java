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
public class PayloadItem{

	@JsonProperty("duration")
	private String duration;

	@JsonProperty("cost")
	private int cost;

	@JsonProperty("startingTime")
	private String startingTime;

	@JsonProperty("endingTime")
	private String endingTime;

	@JsonProperty("callType")
	private String callType;
}