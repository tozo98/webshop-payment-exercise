package com.otp.interview.webshoppaymentexercise.reader;

import com.otp.interview.webshoppaymentexercise.domain.Customer;
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
import java.util.stream.Collectors;

@Component
public class CustomerReader {

    private final Logger logger = LoggerFactory.getLogger(CustomerReader.class);

    @Value("${customers.source.file}")
    private String CUSTOMERS_SOURCE_FILENAME;

    public static final int REQUIRED_FIELDS = 4;

    private static final String SEPARATOR = ";";

    public List<Customer> readCustomersFromFile() {
        List<String> customerStrings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(CUSTOMERS_SOURCE_FILENAME))) {
            while (scanner.hasNextLine()) {
                customerStrings.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customerStrings.stream().map(this::createCustomerFrom).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Customer createCustomerFrom(String line) {
        if (!isValidCustomerRawData(line)) {
            logger.error("Invalid input line: " + line);
            return null;
        }
        String[] fields = line.split(SEPARATOR);
        return new Customer(fields[0], fields[1], fields[2], fields[3]);
    }

    private boolean isValidCustomerRawData(String line) {
        String[] fields = line.split(SEPARATOR);
        return fields.length == REQUIRED_FIELDS;
    }
}
