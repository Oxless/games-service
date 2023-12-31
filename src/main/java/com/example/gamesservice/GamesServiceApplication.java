package com.example.gamesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class GamesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamesServiceApplication.class, args);
    }

}
