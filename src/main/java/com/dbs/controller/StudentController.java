package com.dbs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.dbs.entity.Student;
import com.dbs.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/all")
    public String getAllStudent(Model model) {
        List<Student> studentList = new ArrayList<>();
        studentRepository.findAll().forEach(studentList::add);
        model.addAttribute("studentList", studentList);
        return "students";
    }

    @GetMapping("/new")
    public String getStudentForm(Model model) {
        Student student = new Student();
        student.setResult(false);
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Create New Student");
        return "student_form";
    }

    @PostMapping("/save")
    public String saveStudent(Student student, Model model) {
        studentRepository.save(student);
        return "redirect:/students/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable("id") long id) {
        studentRepository.deleteById(id);
        return "redirect:/students/all";
    }

    @GetMapping("/{id}")
    public String editStudentById(@PathVariable("id") long id, Model model) {
        Student s = studentRepository.findById(id).get();
        model.addAttribute("student", s);
        model.addAttribute("pageTitle", "Edit Student with Id " + id);
        return "student_form";
    }

    @GetMapping("/{id}/result/{status}")
    public String updateResultById(@PathVariable("id") long id, @PathVariable("status") boolean status, Model model) {
        studentRepository.updateResultStatus(id, status);
        return "redirect:/students/all";
    }

    @GetMapping("/keyword")
    public String findStudentByNameContainsKeyword(@Param("keyword") String keyword, Model model) {
        List<Student> studentList = new ArrayList<>();
        studentRepository.findByNameContainingIgnoreCase(keyword).forEach(studentList::add);
        model.addAttribute("studentList", studentList);
        return "students";
    }
}