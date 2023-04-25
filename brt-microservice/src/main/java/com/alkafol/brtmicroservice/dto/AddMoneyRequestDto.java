package com.alkafol.brtmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyRequestDto {
    private long phoneNumber;
    private double money;
}
