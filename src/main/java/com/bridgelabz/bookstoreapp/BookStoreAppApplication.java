package com.bridgelabz.bookstoreapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
@RequestMapping("/api")
public class BookStoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreAppApplication.class, args);
    }

}
