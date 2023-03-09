package ru.hogwarts.schoolHW.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolHW.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    public List<Faculty> findByColor(String color);
}
