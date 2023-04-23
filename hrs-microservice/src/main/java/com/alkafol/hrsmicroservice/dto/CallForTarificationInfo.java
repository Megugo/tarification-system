package com.alkafol.hrsmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CallForTarificationInfo {
    private String callType;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
}
