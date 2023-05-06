package IntegrationTests.api_tests.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tariff {
        private String id;
        private Double basePrice;
        private Double defaultIncomingPrice;
        private Double defaultOutgoingPrice;
        private Integer specialMinutesIncomingAmount;
        private Integer specialMinutesOutgoingAmount;
        private Double specialMinutesIncomingPrice;
        private Double specialMinutesOutgoingPrice;
        private Boolean isSpecialMinutesSplitted;
}
