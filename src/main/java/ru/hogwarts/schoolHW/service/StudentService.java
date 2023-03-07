package ru.hogwarts.schoolHW.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private Map<Long, Student> studentMap = new HashMap<>();

    private Long id = 0L;

    public Student createStudent(Student student){
        student.setId(++id);
        studentMap.put(id, student);
        return student;
    }

    public Student findStudent(Long id){
        return studentMap.get(id);
    }

    public Student editStudent(Student student){
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student removeStudent(Long id){
        return studentMap.remove(id);
    }

    public List<Student> getByAge(int age){
        return studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
