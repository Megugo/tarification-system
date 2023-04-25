package com.alkafol.hrsmicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TariffInfo {
    private String tariffId;
    private double basePrice;
    private int specialMinutesIncomingAmount;
    private int specialMinutesOutgoingAmount;
    private double specialMinutesIncomingPrice;
    private double specialMinutesOutgoingPrice;
    private double defaultIncomingPrice;
    private double defaultOutgoingPrice;
    private boolean isSpecialMinutesSplitted;
}
