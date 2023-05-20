package com.otp.interview.webshoppaymentexercise.writer;

import com.otp.interview.webshoppaymentexercise.domain.Customer;
import com.otp.interview.webshoppaymentexercise.domain.Webshop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ReportWriter {

    public static final int NUM_OF_TOP_CUSTOMERS = 2;

    @Value("${task1.filename}")
    private String TASK1_FILENAME;

    @Value("${task2.filename}")
    private String TASK2_FILENAME;

    @Value("${task3.filename}")
    private String TASK3_FILENAME;

    public void saveCustomerReport(List<Customer> summary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK1_FILENAME))) {
            summary.forEach(customer -> {
                try {
                    writer.write(customer.toString());
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTopCustomers(List<Customer> summary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK2_FILENAME))) {
            for (int i = 0; i < NUM_OF_TOP_CUSTOMERS; i++) {
                try {
                    writer.write(summary.get(i).toString());
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveWebshopReport(Map<String, Webshop> webshops) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK3_FILENAME))) {
            webshops.forEach((id, webshop) -> {
                try {
                    writer.write(webshop.toString());
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
