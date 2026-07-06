package com.example.hxce.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.example.hxce.api")
@ServletComponentScan
public class HxceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HxceApiApplication.class, args);
    }

}
