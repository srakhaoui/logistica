package com.logistica.service;

public class KilometrageInvalideException extends RuntimeException {
    private String key;

    public KilometrageInvalideException(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
