package ru.hogwarts.schoolHW.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolHW.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public List<Student> findByAge(int age);
}
