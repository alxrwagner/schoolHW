package ru.hogwarts.schoolHW.dto;

import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import lombok.Data;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.model.Student;

import javax.persistence.ManyToOne;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private Long facultyId;

    public static StudentDTO fromStudent(Student  student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setAge(student.getAge());
        studentDTO.setFacultyId(student.getFaculty().getId());

        return studentDTO;
    }

    public Student toStudent(){
        Student student = new Student();
        student.setId(this.getId());
        student.setName(this.getName());
        student.setAge(this.getAge());

        return student;
    }
}
