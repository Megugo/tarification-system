package com.alkafol.cdrmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientGenerationRequestDto {
    List<String> baseNumbers;
    int numbersAmount;
}
