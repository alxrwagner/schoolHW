package ru.hogwarts.schoolHW.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolHW.model.Student;
import ru.hogwarts.schoolHW.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private StudentService ss;

    public StudentController(StudentService ss) {
        this.ss = ss;
    }

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable Long id){
        return ss.findStudent(id);
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student){
        return ss.createStudent(student);
    }

    @PutMapping("/students")
    public Student editStudent(@RequestBody Student student){
        return ss.editStudent(student);
    }

    @DeleteMapping("/students/{id}")
    public Student removeStudent(@PathVariable Long id){
         return ss.removeStudent(id);
    }

    @GetMapping("/{age}")
    public List<Student> getStudentsByAge(@PathVariable int age){
        return ss.getByAge(age);
    }
}
