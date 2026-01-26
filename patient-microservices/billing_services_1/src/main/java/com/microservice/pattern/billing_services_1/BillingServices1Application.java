package com.microservice.pattern.billing_services_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class BillingServices1Application {

    // Create a logger instance
    private static final Logger log = LoggerFactory.getLogger(BillingServices1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(BillingServices1Application.class, args);
        // Log after the application starts
        log.info("Billing Service GRPC server started on port 9001");
    }

}
