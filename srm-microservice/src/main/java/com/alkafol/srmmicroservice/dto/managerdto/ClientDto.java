package com.alkafol.srmmicroservice.dto.managerdto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    @Pattern(regexp = "\\d{11,13}")
    private String phoneNumber;
    private String tariffId;
    private double balance;
}
