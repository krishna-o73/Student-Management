package com.dbs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.dbs.entity.Student;
import com.dbs.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping(value = "/all")
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        studentRepository.findAll().forEach(studentList::add);
        return studentList;
    }

    @PostMapping(value = "/save")
    public Student saveStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable("id") long id) {
        studentRepository.deleteById(id);
        return "Student deleted";
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PatchMapping("/{id}/result/{status}")
    public String updateResultById(@PathVariable("id") long id, @PathVariable("status") boolean status) {
        studentRepository.updateResultStatus(id, status);
        return status ? "Pass" : "Fail";
    }

    @GetMapping("/keyword")
    public List<Student> findStudentByNameContainsKeyword(@Param("keyword") String keyword) {
        return studentRepository.findByNameContainingIgnoreCase(keyword);
    }
}