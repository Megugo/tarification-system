package com.alkafol.brtmicroservice.models;

import java.time.LocalDateTime;


public class Call {
    private Integer id;
    private String callType;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;

    public Call(){}

    public Call(String callType, LocalDateTime startingTime, LocalDateTime endingTime) {
        this.callType = callType;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }
}
