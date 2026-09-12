package com.infyvaritaas.ispl;

import com.infyvaritaas.ispl.config.RazorpayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RazorpayProperties.class)
public class IsplApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsplApplication.class, args);
    }

}
