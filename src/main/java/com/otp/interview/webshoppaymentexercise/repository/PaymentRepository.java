package com.otp.interview.webshoppaymentexercise.repository;

import com.otp.interview.webshoppaymentexercise.domain.Payment;
import com.otp.interview.webshoppaymentexercise.reader.PaymentReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentRepository {

    private final PaymentReader paymentReader;

    private List<Payment> payments;

    public PaymentRepository(PaymentReader paymentReader) {
        this.paymentReader = paymentReader;
    }

    public List<Payment> getPayments() {
        if (payments == null) {
            payments = paymentReader.readPaymentsFromFile();
        }
        return payments;
    }
}
