package com.otp.interview.webshoppaymentexercise.domain;

import lombok.Data;

@Data
public final class Customer {
    private static final String SEPARATOR = ", ";
    private String webshopId;
    private String id;
    private String name;
    private String address;
    private Long value;

    public Customer(String webshopId, String id, String name, String address) {
        this.webshopId = webshopId;
        this.id = id;
        this.name = name;
        this.address = address;
        this.value = 0L;
    }

    @Override
    public String toString() {
        return name + SEPARATOR + address + SEPARATOR + value;
    }
}
