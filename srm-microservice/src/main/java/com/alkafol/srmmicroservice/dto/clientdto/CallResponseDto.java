package com.alkafol.srmmicroservice.dto.clientdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CallResponseDto {
    private String callType;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private Time duration;
    private double cost;
}
