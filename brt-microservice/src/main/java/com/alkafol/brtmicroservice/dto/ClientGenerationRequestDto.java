package com.alkafol.brtmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientGenerationRequestDto {
    Set<Long> baseNumbers;
    int numbersAmount;
}
