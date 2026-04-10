package com.proyecto.backend.account.dto;

public class AccountRegisterResponse {

    private final Long id;
    private final String name;
    private final String provider;
    private final String status;

    public AccountRegisterResponse(Long id, String name, String provider, String status) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }

    public String getStatus() {
        return status;
    }
}
