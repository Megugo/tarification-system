package com.alkafol.hrsmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ClientTarificationResult {
    private long phoneNumber;
    private String tariffIndex;
    private List<CallInfo> payload;
    private double totalCost;
    private String monetaryUnit;
}
