package com.alkafol.brtmicroservice.entities;

import jakarta.persistence.*;

import java.util.Map;

@Entity
public class Tariff {
    @Id
    private String id;
    private Double basePrice;
    private Double defaultIncomingPrice;
    private Double defaultOutgoingPrice;
    private Integer specialMinutesIncomingAmount;
    private Integer specialMinutesOutgoingAmount;
    private Double specialMinutesIncomingPrice;
    private Double specialMinutesOutgoingPrice;
    private Boolean isSpecialMinutesSplitted;

    // поле для расширения (тариф X), позволяет добавлять дополнительные параметры
    // их можно обрабатывать в hrs сделав Injection handler'ов в Map по ключу-названию параметра
    @ElementCollection
    Map<String, String> additionalParameters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getSpecialMinutesIncomingAmount() {
        return specialMinutesIncomingAmount;
    }

    public void setSpecialMinutesIncomingAmount(Integer specialMinutesIncomingAmount) {
        this.specialMinutesIncomingAmount = specialMinutesIncomingAmount;
    }

    public Double getSpecialMinutesIncomingPrice() {
        return specialMinutesIncomingPrice;
    }

    public void setSpecialMinutesIncomingPrice(Double specialMinutesIncomingPrice) {
        this.specialMinutesIncomingPrice = specialMinutesIncomingPrice;
    }

    public Double getSpecialMinutesOutgoingPrice() {
        return specialMinutesOutgoingPrice;
    }

    public void setSpecialMinutesOutgoingPrice(Double specialMinutesOutgoingPrice) {
        this.specialMinutesOutgoingPrice = specialMinutesOutgoingPrice;
    }

    public Double getDefaultIncomingPrice() {
        return defaultIncomingPrice;
    }

    public void setDefaultIncomingPrice(Double defaultIncomingPrice) {
        this.defaultIncomingPrice = defaultIncomingPrice;
    }

    public Double getDefaultOutgoingPrice() {
        return defaultOutgoingPrice;
    }

    public void setDefaultOutgoingPrice(Double defaultOutgoingPrice) {
        this.defaultOutgoingPrice = defaultOutgoingPrice;
    }

    public Map<String, String> getOperatorsWithFreeCalls() {
        return additionalParameters;
    }

    public void setOperatorsWithFreeCalls(Map<String, String> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }

    public Integer getSpecialMinutesOutgoingAmount() {
        return specialMinutesOutgoingAmount;
    }

    public void setSpecialMinutesOutgoingAmount(Integer specialMinutesOutgoingAmount) {
        this.specialMinutesOutgoingAmount = specialMinutesOutgoingAmount;
    }

    public Boolean getSpecialMinutesSplitted() {
        return isSpecialMinutesSplitted;
    }

    public void setSpecialMinutesSplitted(Boolean specialMinutesSplitted) {
        isSpecialMinutesSplitted = specialMinutesSplitted;
    }

    public Map<String, String> getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(Map<String, String> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }
}
