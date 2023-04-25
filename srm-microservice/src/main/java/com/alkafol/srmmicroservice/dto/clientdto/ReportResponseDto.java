package com.alkafol.srmmicroservice.dto.clientdto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {
    private long id;
    private long phoneNumber;
    private String tariffIndex;
    private List<CallResponseDto> payload;
    private double totalCost;
    private String monetaryUnit;
}
