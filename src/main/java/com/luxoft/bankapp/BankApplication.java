package com.luxoft.bankapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@SpringBootApplication
@PropertySource("classpath:clients.properties")
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);

    }

}
