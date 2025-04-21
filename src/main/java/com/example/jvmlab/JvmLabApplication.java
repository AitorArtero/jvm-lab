package com.example.jvmlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JvmLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(JvmLabApplication.class, args);
    }
}