package com.alkafol.srmmicroservice.dto.managerdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CallInfo {
    private String callType;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private String duration;

}
