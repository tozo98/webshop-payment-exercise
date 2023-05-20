package com.otp.interview.webshoppaymentexercise.domain;

import lombok.Data;


@Data
public class Webshop {

    private static final String SEPARATOR = ", ";
    private String id;
    private Long purchaseWitCard;

    private Long purchaseWithTransfer;

    public Webshop(String id) {
        this.id = id;
        this.purchaseWitCard = 0L;
        this.purchaseWithTransfer = 0L;
    }

    @Override
    public String toString() {
        return id + SEPARATOR + purchaseWitCard + SEPARATOR + purchaseWithTransfer;
    }

}
