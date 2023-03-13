package ru.hogwarts.schoolHW.dto;

import lombok.Data;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.model.Student;

import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FacultyDTO {
    private Long id;
    private String name;
    private String color;
    public static FacultyDTO fromFaculty(Faculty faculty){
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setName(faculty.getName());
        facultyDTO.setColor(faculty.getColor());

        return facultyDTO;
    }

    public Faculty toFaculty(){
        Faculty faculty = new Faculty();
        faculty.setId(this.getId());
        faculty.setName(this.getName());
        faculty.setColor(this.getColor());

        return faculty;
    }
}
