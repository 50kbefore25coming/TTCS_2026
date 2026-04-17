package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

    // Bắt lỗi cụ thể
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> xuLyLoiKhongTimThay(NoSuchElementException ex) {

        // http 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sinh viên!");
    }

    // Bắt các lỗi còn lại
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> xuLyLoiChung(Exception ex) {
        // http 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + ex.getMessage());
    }
}