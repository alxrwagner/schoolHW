package ru.hogwarts.schoolHW.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.schoolHW.dto.FacultyDTO;
import ru.hogwarts.schoolHW.dto.StudentDTO;
import ru.hogwarts.schoolHW.model.Student;
import ru.hogwarts.schoolHW.repository.FacultyRepository;
import ru.hogwarts.schoolHW.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public StudentDTO createStudent(StudentDTO studentDTO){
        studentDTO.setId(null);
        Student student = studentDTO.toStudent();
        student.setFaculty(facultyRepository.findById(studentDTO.getFacultyId()).orElse(null));
        return StudentDTO.fromStudent(studentRepository.save(student));
    }

    public StudentDTO findStudent(Long id){
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null){
            return StudentDTO.fromStudent(student);
        }
        return null;
    }

    public StudentDTO editStudent(StudentDTO studentDTO){
        Student student = studentDTO.toStudent();
        return StudentDTO.fromStudent(studentRepository.save(student));
    }

    public void removeStudent(Long id){
         studentRepository.deleteById(id);
    }

    public List<StudentDTO> getByAge(Integer age){
        List<Student> students = studentRepository.findByAge(age);
        List<StudentDTO> studentDTOs = new ArrayList<>();
        return studentDTOs = students.stream().map(StudentDTO::fromStudent).collect(Collectors.toList());
    }

    public List<StudentDTO> findByAgeBetween(Integer min, Integer max){
        List<Student> students = studentRepository.findByAgeBetween(min, max);
        List<StudentDTO> studentDTOs = new ArrayList<>();
        return studentDTOs = students.stream().map(StudentDTO::fromStudent).collect(Collectors.toList());
    }

    public FacultyDTO findFacultyByStudentId(Long id){
        StudentDTO studentDTO = StudentDTO.fromStudent(studentRepository.findById(id).orElse(null));
        if (studentDTO != null){
            return FacultyDTO.fromFaculty(facultyRepository.findById(studentDTO.getFacultyId()).orElse(null));
        }
        return null;
    }
}
