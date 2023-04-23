package com.alkafol.hrsmicroservice.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientStats {
    private int leftedOutgoingSpecialMinutes;
    private int leftedIncomingSpecialMinutes;
}