package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
    // dữ liệu mẫu
    @Bean
    CommandLineRunner init(StudentRepository repo) {

        return args -> {
            if (repo.count() == 0) {
                Student s = new Student();
                s.setName("Nguyen haha");
                s.setEmail("haha@stu.ptit.edu.vn");
                repo.save(s);
            }
        };
    }
}