package com.flyingrain.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.flyingrain")
public class BootStrap {
    public static void main(String[] args) {
        new SpringApplication(BootStrap.class).run(args);
    }
}