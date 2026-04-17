package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên sinh viên
    @Column(nullable = false)
    private String name;

    // Email
    @Column(nullable = false, unique = true)
    private String email;
}