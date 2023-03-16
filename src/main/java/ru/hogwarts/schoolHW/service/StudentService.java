package ru.hogwarts.schoolHW.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    static final Logger logger = LoggerFactory.getLogger(StudentRepository.class);

    @Autowired
    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        logger.info("Was invoke method for create student");

        studentDTO.setId(null);
        Student student = studentDTO.toStudent();
        student.setFaculty(facultyRepository.findById(studentDTO.getFacultyId()).orElse(null));
        return StudentDTO.fromStudent(studentRepository.save(student));
    }

    @Transactional
    public StudentDTO findStudent(Long id) {
        logger.info("Was invoke method for find student by ID");

        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            return StudentDTO.fromStudent(student);
        }
        return null;
    }

    @Transactional
    public StudentDTO editStudent(StudentDTO studentDTO) {
        logger.info("Was invoke method for edit student");

        Student student = studentDTO.toStudent();
        return StudentDTO.fromStudent(studentRepository.save(student));
    }

    @Transactional
    public void removeStudent(Long id) {
        logger.info("Was invoke method for delete student by ID");

        studentRepository.deleteById(id);
    }

    @Transactional
    public List<StudentDTO> getByAge(Integer age) {
        logger.info("Was invoke method for find student by age");

        List<Student> students = studentRepository.findByAge(age);
        List<StudentDTO> studentDTOs = new ArrayList<>();
        return studentDTOs = students.stream().map(StudentDTO::fromStudent).collect(Collectors.toList());
    }

    @Transactional
    public List<StudentDTO> findByAgeBetween(Integer min, Integer max) {
        logger.info("Was invoke method for find student by age between");

        List<Student> students = studentRepository.findByAgeBetween(min, max);
        List<StudentDTO> studentDTOs = new ArrayList<>();
        return studentDTOs = students.stream().map(StudentDTO::fromStudent).collect(Collectors.toList());
    }

    @Transactional
    public FacultyDTO findFacultyByStudentId(Long id) {
        logger.info("Was invoke method for find faculty by student's ID");

        StudentDTO studentDTO = StudentDTO.fromStudent(studentRepository.findById(id).orElse(null));
        if (studentDTO != null) {
            return FacultyDTO.fromFaculty(facultyRepository.findById(studentDTO.getFacultyId()).orElse(null));
        }
        return null;
    }

    @Transactional
    public Long getCountStudents() {
        logger.info("Was invoke method for get total count students");

        return studentRepository.getCountStudents();
    }

    @Transactional
    public Long getMiddleAge() {
        logger.info("Was invoke method for get middle age all students");

        return studentRepository.getMiddleAge();
    }

    @Transactional
    public List<StudentDTO> getYoungestStudents() {
        logger.info("Was invoke method for find 5 youngest students");

        List<Student> students = studentRepository.getYoungestStudents();
        return students.stream().map(StudentDTO::fromStudent).collect(Collectors.toList());
    }

    @Transactional
    public List<StudentDTO> getAllStudents(Pageable pageable) {
        logger.info("Was invoke method for find all students by page");

        return studentRepository.findAll(pageable).getContent().stream().map(StudentDTO::fromStudent).collect(Collectors.toList());
    }
}
