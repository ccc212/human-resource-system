package com.hrsys.enums;

public enum OperationType {
    REGISTRATION("登记"),
    REVIEW("复核"),
    UPDATE("更新");
    private final String description;

    OperationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
