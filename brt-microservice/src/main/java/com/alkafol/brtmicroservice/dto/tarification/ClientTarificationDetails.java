package com.alkafol.brtmicroservice.dto.tarification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientTarificationDetails {
    private long phoneNumber;
    private String tariffIndex;
    private List<CallInfo> payload;
    private double totalCost;
    private String monetaryUnit;
}
