package com.alkafol.srmmicroservice.dto.clientdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyRequestDto {
    @Pattern(regexp = "\\d{11,13}")
    private String phoneNumber;

    @Min(1)
    private double money;
}
