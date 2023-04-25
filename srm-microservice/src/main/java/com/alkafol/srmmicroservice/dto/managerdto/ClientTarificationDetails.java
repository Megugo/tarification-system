package com.alkafol.srmmicroservice.dto.managerdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientTarificationDetails {
    private long number;
    private String tariffId;
    private List<CallInfo> callInfoList;
    private double totalCost;
    private String monetaryUnit;
}
