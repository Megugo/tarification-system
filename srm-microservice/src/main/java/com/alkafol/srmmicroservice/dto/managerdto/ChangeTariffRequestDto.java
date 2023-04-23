package com.alkafol.srmmicroservice.dto.managerdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTariffRequestDto {
    private long phoneNumber;
    private String tariffId;
}
