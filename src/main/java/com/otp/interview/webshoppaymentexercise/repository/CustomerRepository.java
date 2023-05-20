package com.otp.interview.webshoppaymentexercise.repository;

import com.otp.interview.webshoppaymentexercise.domain.Customer;
import com.otp.interview.webshoppaymentexercise.reader.CustomerReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerRepository {

    private final CustomerReader customerReader;

    private List<Customer> customers;

    public CustomerRepository(CustomerReader customerReader) {
        this.customerReader = customerReader;
    }

    public List<Customer> getCustomers() {
        if (customers == null) {
            customers = customerReader.readCustomersFromFile();
        }
        return customers;
    }
}
