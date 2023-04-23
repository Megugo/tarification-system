package com.alkafol.brtmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyResponseDto {
    private long phoneNumber;
    private double money;
}