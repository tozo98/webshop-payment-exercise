package com.otp.interview.webshoppaymentexercise.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Payment {
    private String webshopId;
    private String customerId;
    private PaymentType type;
    private Long value;
    private String accountNumber;
    private String cardNumber;
    private String date;
}
