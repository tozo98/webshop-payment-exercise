package com.otp.interview.webshoppaymentexercise.writer;

import com.otp.interview.webshoppaymentexercise.domain.Customer;
import com.otp.interview.webshoppaymentexercise.domain.Webshop;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class ConsoleWriter {

    private static final int NUM_OF_TOP_CUSTOMERS = 2;

    public void printCustomerReport(List<Customer> summary) {
        System.out.println("Customer report:");
        summary.forEach(System.out::println);
    }

    public void printTopCustomers(List<Customer> sortedSummary) {
        System.out.println("Top customers:");
        for (int i = 0; i < NUM_OF_TOP_CUSTOMERS; i++) {
            System.out.println(sortedSummary.get(i));
        }
    }

    public void printWebshopReport(Map<String, Webshop> webshops) {
        System.out.println("Webshop report:");
        webshops.forEach((key, value) -> System.out.println(value));
    }
}
