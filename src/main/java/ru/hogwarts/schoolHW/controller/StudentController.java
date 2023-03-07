package ru.hogwarts.schoolHW.controller;

import ch.qos.logback.core.joran.conditional.IfAction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = ss.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return ss.createStudent(student);
    }

    @PutMapping("/students")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student student1 = ss.editStudent(student);
        if (student1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("/students/{id}")
    public Student removeStudent(@PathVariable Long id) {
        return ss.removeStudent(id);
    }

    @GetMapping
    public List<Student> getStudentsByAge(@RequestParam int age) {
        return ss.getByAge(age);
    }
}
