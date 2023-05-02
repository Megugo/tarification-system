package com.alkafol.brtmicroservice.dto.tarification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TarificationResultEntry {
    private String phoneNumber;
    private double balance;
}
