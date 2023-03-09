package ru.hogwarts.schoolHW.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolHW.model.Student;
import ru.hogwarts.schoolHW.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student findStudent(Long id){
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student){
        return studentRepository.save(student);
    }

    public void removeStudent(Long id){
         studentRepository.deleteById(id);
    }

    public List<Student> getByAge(int age){
        return studentRepository.findByAge(age);
    }
}
