package com.otp.interview.webshoppaymentexercise.reader;

import com.otp.interview.webshoppaymentexercise.domain.Payment;
import com.otp.interview.webshoppaymentexercise.domain.PaymentType;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class PaymentReader {

    private final Logger logger = LoggerFactory.getLogger(PaymentReader.class);
    @Value("${payments.source.file}")
    private String PAYMENTS_SOURCE_FILENAME;
    private static final int REQUIRED_FIELDS = 7;
    private static final String SEPARATOR = ";";

    public List<Payment> readPaymentsFromFile() {
        List<String> customerStrings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(PAYMENTS_SOURCE_FILENAME))) {
            while (scanner.hasNextLine()) {
                customerStrings.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customerStrings.stream().map(this::createPaymentFrom).map(this::validatePayment).filter(Objects::nonNull).toList();
    }

    private Payment validatePayment(Payment payment) {
        if (!Objects.nonNull(payment) || (payment.getType() == PaymentType.CARD && payment.getCardNumber().isEmpty()) ||
                (payment.getType() == PaymentType.TRANSFER && payment.getAccountNumber().isEmpty())) {
            logger.error("Invalid payment: " + payment);
            return null;
        }
        return payment;
    }

    private Payment createPaymentFrom(String line) {
        if (!isValidPaymentRawData(line)) {
            logger.error("Invalid input line: " + line);
            return null;
        }
        String[] fields = line.split(SEPARATOR);
        return new Payment(fields[0], fields[1], PaymentType.valueOf(fields[2].toUpperCase()), Long.valueOf(fields[3]), fields[4], fields[5], fields[6]);
    }

    private boolean isValidPaymentRawData(String line) {
        String[] fields = line.split(SEPARATOR);
        return fields.length == REQUIRED_FIELDS && NumberUtils.isParsable(fields[3]);
    }
}
