package com.white.stratego.stratego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class StrategoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrategoApplication.class, args);
    }

}
