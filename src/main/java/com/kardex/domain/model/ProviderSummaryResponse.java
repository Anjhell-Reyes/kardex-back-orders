package com.kardex.domain.model;

public class ProviderSummaryResponse {
    private final Long id;
    private final String companyName;
    private final String email;

    public ProviderSummaryResponse(Long id, String companyName, String email) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }
    public String getEmail() {
        return email;
    }
}
