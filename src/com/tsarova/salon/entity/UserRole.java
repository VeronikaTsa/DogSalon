package com.tsarova.salon.entity;

/**
 * @author Veronika Tsarova
 */
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
