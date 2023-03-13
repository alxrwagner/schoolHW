package ru.hogwarts.schoolHW.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolHW.dto.StudentDTO;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);
    List<Student> findByAgeBetween(Integer min, Integer max);

}
