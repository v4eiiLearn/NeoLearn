package ru.neoflex.enums;

public enum UserRole {
    CREDIT_MANAGER,
    SUPERVISOR;

    private UserRole() {
    }

    public String value() {
        return this.name();
    }

    public static UserRole fromValue(String v) {
        return valueOf(v);
    }
}
