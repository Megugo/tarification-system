package com.alkafol.hrsmicroservice.models;

public enum MonetaryUnits {
    RUB("rubles");

    private final String name;

    MonetaryUnits(String rubles) {
        this.name = name();
    }

    public String getName() {
        return name;
    }
}
