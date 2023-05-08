package IntegrationTests.api_tests.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumbersItem{

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("balance")
	private int balance;
}