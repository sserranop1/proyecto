package com.proyecto.backend.account;

public enum AccountProvider {
    AWS,
    GCP;

    public static AccountProvider from(String value) {
        try {
            return AccountProvider.valueOf(value.trim().toUpperCase());
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("provider must be AWS or GCP");
        }
    }
}
