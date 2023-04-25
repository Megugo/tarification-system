package com.alkafol.brtmicroservice.dto.tarification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TarificationResult {
    private List<TarificationResultEntry> numbers;
}
