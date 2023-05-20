package com.otp.interview.webshoppaymentexercise.report;

import com.otp.interview.webshoppaymentexercise.domain.Customer;
import com.otp.interview.webshoppaymentexercise.domain.Payment;
import com.otp.interview.webshoppaymentexercise.domain.PaymentType;
import com.otp.interview.webshoppaymentexercise.domain.Webshop;
import com.otp.interview.webshoppaymentexercise.repository.CustomerRepository;
import com.otp.interview.webshoppaymentexercise.repository.PaymentRepository;
import com.otp.interview.webshoppaymentexercise.writer.ConsoleWriter;
import com.otp.interview.webshoppaymentexercise.writer.ReportWriter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ReportCreatorService {

    private final Logger logger = LoggerFactory.getLogger(ReportCreatorService.class);

    private final ReportWriter reportWriter;

    private final ConsoleWriter consoleWriter;

    private final CustomerRepository customerRepository;

    private final PaymentRepository paymentRepository;


    public void printAndSaveCustomerReport() {
        processCustomersAndPayments();
        List<Customer> summary = customerRepository.getCustomers();
        reportWriter.saveCustomerReport(summary);
        consoleWriter.printCustomerReport(summary);
    }

    public void printAndSaveTopCustomers() {
        List<Customer> summary = customerRepository.getCustomers();
        reportWriter.saveTopCustomers(summary);
        consoleWriter.printTopCustomers(summary);
    }

    public void printAndSaveWebshopReport() {
        Map<String, Webshop> webshops = createWebshopReport();
        reportWriter.saveWebshopReport(webshops);
        consoleWriter.printWebshopReport(webshops);
    }

    private void processCustomersAndPayments() {
        List<Customer> customers = customerRepository.getCustomers();
        List<Payment> payments = paymentRepository.getPayments();
        customers.forEach(customer -> payments.forEach(payment -> {
            if (payment.getWebshopId().equals(customer.getWebshopId()) && payment.getCustomerId().equals(customer.getId())) {
                customer.setValue(customer.getValue() + payment.getValue());
            }
        }));
        customers.sort(Comparator.comparing(Customer::getValue).reversed());
    }

    private Map<String, Webshop> createWebshopReport() {
        Map<String, Webshop> webshops = new HashMap<>();
        List<Payment> payments = paymentRepository.getPayments();
        payments.forEach(payment -> {
            Webshop webshop;
            if (webshops.containsKey(payment.getWebshopId())) {
                webshop = webshops.get(payment.getWebshopId());
            } else {
                webshop = new Webshop(payment.getWebshopId());
            }
            if (checkIfPaymentHasUnknownIds(payment)) {
                logger.error("Payment has unknown Ids: " + payment);
                return;
            }
            if (payment.getType().equals(PaymentType.TRANSFER)) {
                webshop.setPurchaseWithTransfer(payment.getValue() + webshop.getPurchaseWithTransfer());
            } else {
                webshop.setPurchaseWitCard(payment.getValue() + webshop.getPurchaseWitCard());
            }
            webshops.put(payment.getWebshopId(), webshop);
        });
        return webshops;
    }

    private boolean checkIfPaymentHasUnknownIds(Payment payment) {
        List<List<String>> collected = customerRepository.getCustomers().stream().map(customer -> Arrays.asList(customer.getWebshopId(), customer.getId())).toList();
        return !collected.contains(Arrays.asList(payment.getWebshopId(), payment.getCustomerId()));
    }
}
