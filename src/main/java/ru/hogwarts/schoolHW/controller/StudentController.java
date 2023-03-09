package ru.hogwarts.schoolHW.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolHW.model.Student;
import ru.hogwarts.schoolHW.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService ss;

    public StudentController(StudentService ss) {
        this.ss = ss;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = ss.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping("/create")
    public Student createStudent(@RequestBody Student student) {
        return ss.createStudent(student);
    }

    @PutMapping("/edit")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student student1 = ss.editStudent(student);
        if (student1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        ss.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Student> getStudentsByAge(@RequestParam int age) {
        return ss.getByAge(age);
    }
}
