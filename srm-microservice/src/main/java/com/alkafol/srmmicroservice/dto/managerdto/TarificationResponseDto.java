package com.alkafol.srmmicroservice.dto.managerdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TarificationResponseDto {
    private List<ClientTarificationDetails> tarification;
}
