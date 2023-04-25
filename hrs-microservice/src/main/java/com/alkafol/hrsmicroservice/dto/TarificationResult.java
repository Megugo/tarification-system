package com.alkafol.hrsmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TarificationResult {
    private List<ClientTarificationResult> tarification;
}
