package com.tsarova.salon.entity;

public enum UserRole {
    ADMINISTRATOR("administrator"),
    EXPERT("expert"),
    USER("user");

    private String value;
    UserRole(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
