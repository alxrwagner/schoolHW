package ru.hogwarts.schoolHW.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolHW.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();

    private Long id = 0L;

    public Faculty createFaculty(Faculty faculty){
        faculty.setId(++id);
        facultyMap.put(id, faculty);
        return faculty;
    }

    public Faculty findFaculty(Long id){
        return facultyMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty){
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty removeFaculty(Long id){
        return facultyMap.remove(id);
    }

    public List<Faculty> getFacultyByColor(String color){
        return facultyMap.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
