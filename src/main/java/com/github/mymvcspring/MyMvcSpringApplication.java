package com.github.mymvcspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MyMvcSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMvcSpringApplication.class, args);
    }

}
