package ru.hogwarts.schoolHW.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.repository.FacultyRepository;

import java.util.List;

@Service
public class HouseService {
    private final FacultyRepository facultyRepository;

    public HouseService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id){
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty){
       return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id){
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFacultyByColor(String color){
        return facultyRepository.findByColor(color);
    }
}
