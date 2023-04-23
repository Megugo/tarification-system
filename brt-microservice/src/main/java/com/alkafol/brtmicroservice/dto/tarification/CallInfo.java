package com.alkafol.brtmicroservice.dto.tarification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CallInfo {
    private String callType;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private String duration;
    private double cost;
}
