package com.alkafol.srmmicroservice.dto.managerdto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewClientDto {
    private long phoneNumber;
    private String tariffId;

    @Min(1)
    private double balance;
}
