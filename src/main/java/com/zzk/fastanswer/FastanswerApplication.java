package com.zzk.fastanswer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FastanswerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastanswerApplication.class, args);
        System.out.println("http://localhost:8080");
    }

}
