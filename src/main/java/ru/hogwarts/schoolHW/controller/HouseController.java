package ru.hogwarts.schoolHW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.service.HouseService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class HouseController {
    private HouseService hs;

    public HouseController(HouseService hs) {
        this.hs = hs;
    }

    @GetMapping("/faculties/{id}")
    public Faculty getFacultyById(@PathVariable Long id) {
        return hs.findFaculty(id);
    }

    @PostMapping("/faculties")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return hs.createFaculty(faculty);
    }

    @PutMapping("/faculties")
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return hs.editFaculty(faculty);
    }

    @DeleteMapping("/faculties/{id}")
    public Faculty removeFaculty(@PathVariable Long id) {
        return hs.removeFaculty(id);
    }

    @GetMapping("/{color}")
    public List<Faculty> getFacultyByColor(@PathVariable String color) {
        return hs.getFacultyByColor(color);
    }
}
