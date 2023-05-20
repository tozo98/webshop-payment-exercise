package com.otp.interview.webshoppaymentexercise;

import com.otp.interview.webshoppaymentexercise.report.ReportCreatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopPaymentExerciseApplication {

    public static void main(String[] args) {
        ReportCreatorService reportCreatorService = SpringApplication.run(WebshopPaymentExerciseApplication.class, args).getBean(ReportCreatorService.class);

        reportCreatorService.printAndSaveCustomerReport();
        reportCreatorService.printAndSaveTopCustomers();
        reportCreatorService.printAndSaveWebshopReport();
    }

}
