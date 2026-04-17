package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // API 1: Thêm sinh viên
    // Method: POST  |  URL: /api/students
    @PostMapping
    public ResponseEntity<Student> themSinhVien(@RequestBody Student student) {

        Student daLuu = studentRepository.save(student);
        // Trả về HTTP 201 và object sinh viên vừa lưu
        return ResponseEntity.status(HttpStatus.CREATED).body(daLuu);
    }

    // API 2: LẤY SINH VIÊN THEO ID  ← CÓ BẮT LỖI
    // Method: GET  |  URL: /api/students/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Student> layTheoId(@PathVariable Long id) {

        // .get() sẽ ném ra lỗi NoSuchElementException nếu ID không tồn tại
        Student sinhVien = studentRepository.findById(id).get();
        return ResponseEntity.ok(sinhVien); // HTTP 200 + JSON sinh viên
    }

    // API 3: Tìm kiếm theo tên
    @GetMapping("/search")
    public ResponseEntity<List<Student>> timKiem(@RequestParam String keyword) {

        List<Student> ketQua = studentRepository.timKiemTheoTen(keyword);
        return ResponseEntity.ok(ketQua);
    }
}