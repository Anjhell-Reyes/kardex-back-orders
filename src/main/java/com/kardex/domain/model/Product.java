package com.kardex.domain.model;

public class Product {
    private final Long id;
    private final String userId;
    private final String name;
    private final Double price;
    private final String imageUrl;
    private final ProviderSummaryResponse provider;

    public Product(Long id, String userId, String name, Double price, String imageUrl, ProviderSummaryResponse provider) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProviderSummaryResponse getProvider() {
        return provider;
    }
}
