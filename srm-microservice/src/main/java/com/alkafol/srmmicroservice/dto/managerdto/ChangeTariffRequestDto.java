package com.alkafol.srmmicroservice.dto.managerdto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTariffRequestDto {
    @Pattern(regexp = "\\d{11,13}")
    private String phoneNumber;
    private String tariffId;
}
