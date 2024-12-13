package com.flyingrain.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.flyingrain")
public class BootStrap {

    private static final Logger logger = LoggerFactory.getLogger(BootStrap.class);

    public static void main(String[] args) {
        logger.info("BootStrap start...");
        new SpringApplication(BootStrap.class).run(args);
        logger.info("BootStrap started!");
    }
}