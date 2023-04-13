package com.example.demo.student;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE("1"),
    FEMALE("2"),
    OTHER("3");

    String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @JsonCreator
    public static Gender getGenderFromCode(String value) {

        for (Gender gender : Gender.values()) {
            if (gender.getCode().equals(value)) {
                return gender;
            }
        }

        return null;
    }

}
