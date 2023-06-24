package com.example.booksearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSearchApplication.class, args);
    }

}
