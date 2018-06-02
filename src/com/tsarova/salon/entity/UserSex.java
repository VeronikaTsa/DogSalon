package com.tsarova.salon.entity;

/**
 * @author Veronika Tsarova
 */
public enum UserSex {
    MALE("male"),
    FEMALE("female");

    private String value;

    UserSex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
