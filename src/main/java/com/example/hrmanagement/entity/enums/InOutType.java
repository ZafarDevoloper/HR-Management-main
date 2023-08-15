package com.example.hrmanagement.entity.enums;

public enum InOutType {

    IN,
    OUT;

    public static InOutType getType(String type) {
        return switch (type) {
            case "in" -> IN;
            case "out" -> OUT;
            default -> null;
        };
    }
}
