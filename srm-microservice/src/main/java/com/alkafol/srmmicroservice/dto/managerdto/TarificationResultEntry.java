package com.alkafol.srmmicroservice.dto.managerdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TarificationResultEntry {
    private long phoneNumber;
    private double balance;
}
