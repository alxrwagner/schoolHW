package ru.hogwarts.schoolHW.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.schoolHW.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);
    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT count(s) FROM student as s", nativeQuery = true)
    Long getCountStudents();

    @Query(value = "SELECT AVG(s.age) FROM student s", nativeQuery = true)
    Long getMiddleAge();

    @Query(value = "SELECT * FROM student ORDER BY age LIMIT 5", nativeQuery = true)
    List<Student> getYoungestStudents();

}
