package ru.hogwarts.schoolHW.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.service.HouseService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class HouseController {
    private final HouseService hs;

    public HouseController(HouseService hs) {
        this.hs = hs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Faculty faculty = hs.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping("/create")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return hs.createFaculty(faculty);
    }

    @PutMapping("/edit")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty faculty1 = hs.editFaculty(faculty);
        if (faculty1 == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        hs.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Faculty> getFacultyByColor(@RequestParam String color) {
        return hs.getFacultyByColor(color);
    }
}
