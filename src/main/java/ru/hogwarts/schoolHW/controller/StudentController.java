package ru.hogwarts.schoolHW.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolHW.dto.FacultyDTO;
import ru.hogwarts.schoolHW.dto.StudentDTO;
import ru.hogwarts.schoolHW.model.Student;
import ru.hogwarts.schoolHW.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService ss;

    public StudentController(StudentService ss) {
        this.ss = ss;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = ss.findStudent(id);
        if (studentDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping("/create")
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return ss.createStudent(studentDTO);
    }

    @PutMapping("/edit")
    public ResponseEntity<StudentDTO> editStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO student = ss.editStudent(studentDTO);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        ss.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudentsByAge(@RequestParam Integer min, @RequestParam(required = false) Integer max) {
        if (min != null && max != null) {
            return ResponseEntity.ok(ss.findByAgeBetween(min, max));
        } else if (min != null) {
            return ResponseEntity.ok(ss.getByAge(min));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<FacultyDTO> findFacultyByStudentId(@PathVariable Long id) {
        FacultyDTO facultyDTO = ss.findFacultyByStudentId(id);
        return ResponseEntity.ok(facultyDTO);
    }

    @GetMapping("/count")
    public Long getCountStudents() {
        return ss.getCountStudents();
    }

    @GetMapping("/middle-age")
    public Long getMiddleAge() {
        return ss.getMiddleAge();
    }

    @GetMapping("/youngest")
    public List<StudentDTO> getYoungest() {
        return ss.getYoungestStudents();
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(@PageableDefault(size = 50) Pageable pageable) {
        List<StudentDTO> studentDTOS = ss.getAllStudents(pageable);
        return ResponseEntity.ok(studentDTOS);
    }
}
