package ru.hogwarts.schoolHW.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolHW.dto.FacultyDTO;
import ru.hogwarts.schoolHW.dto.StudentDTO;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.service.HouseService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("faculty")
public class HouseController {
    private final HouseService hs;

    public HouseController(HouseService hs) {
        this.hs = hs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Long id) {
        FacultyDTO facultyDTO = hs.findFaculty(id);
        if (facultyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyDTO);
    }

    @PostMapping("/create")
    public FacultyDTO createFaculty(@RequestBody FacultyDTO facultyDTO) {
        return hs.createFaculty(facultyDTO);
    }

    @PutMapping("/edit")
    public ResponseEntity<FacultyDTO> editFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO faculty = hs.editFaculty(facultyDTO);
        if (faculty == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<FacultyDTO> removeFaculty(@PathVariable Long id) {
        hs.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FacultyDTO>> getFacultyByColor(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        if (color != null && name != null){
            return ResponseEntity.ok(Stream.of(hs.getFacultyByColor(color), hs.findByName(name)).flatMap(Collection::stream).distinct().collect(Collectors.toList()));
        }
        if (color != null){
            return ResponseEntity.ok(hs.getFacultyByColor(color));
        }
        if(name != null){
            return ResponseEntity.ok(hs.findByName(name));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByFacultyId(@PathVariable Long id){
        List<StudentDTO> students = hs.getStudentsByFacultyId(id);
        if (students != null){
            return ResponseEntity.ok(students);
        }
        return ResponseEntity.badRequest().build();
    }
}
