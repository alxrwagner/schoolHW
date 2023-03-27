package ru.hogwarts.schoolHW.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.model.Student;
import ru.hogwarts.schoolHW.repository.FacultyRepository;
import ru.hogwarts.schoolHW.repository.StudentRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;

    private JSONObject jsonObject;
    @Autowired
    MockMvc mockMvc;

    private Student student;
    private Faculty faculty;

    @BeforeEach
    void setUp() throws JSONException {
        faculty = new Faculty();
        faculty.setColor("Green");
        faculty.setName("Alchemy");
        facultyRepository.save(faculty);

        student = new Student();
        student.setName("TestStudentName");
        student.setAge(24);
        student.setFaculty(faculty);
        studentRepository.save(student);

        jsonObject = new JSONObject();
        jsonObject.put("name", "test_name");
        jsonObject.put("age", 25);
        jsonObject.put("facultyId", faculty.getId());

    }

    @Test
    void whenUserAdded_thenItExistsInList() throws Exception {

        mockMvc.perform(post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("test_name"));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("test_name"));
    }

    @Test
    void whenFindStudentById_ThenGetStudent() throws Exception {
        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TestStudentName"));
    }

    @Test
    void whenFindStudentById_ThenStatusNotFoundIfStudentIsNotFound() throws Exception {
        studentRepository.delete(student);
        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEditStudent() throws Exception {

        jsonObject.put("name", "TestName");
        jsonObject.put("age", 37);

        mockMvc.perform(put("/student/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.age").value(37))
                .andExpect(jsonPath("$.facultyId").value(faculty.getId()));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("TestName"))
                .andExpect(jsonPath("$[1].age").value(37))
                .andExpect(jsonPath("$[1].facultyId").value(faculty.getId()));
    }

    @Test
    void whenEditStudent_ThenReturnStatusBadRequestIfParameterStudentIsNull() throws Exception {
        mockMvc.perform(put("/student/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUpdateStudent_ThenStatusBadRequestIfStudentIsNotFound() throws Exception {
        studentRepository.delete(student);

        mockMvc.perform(put("/student/edit"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRemoveStudent() throws Exception {
        mockMvc.perform(delete("/student/remove/" + student.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/student?pageNumber=1&Size=1"))
                .andExpect(jsonPath("$.length()").value(0))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetStudentsByAge() throws Exception {
        mockMvc.perform(get("/student?min=" + student.getAge() + "&pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].age").value(24));
    }

    @Test
    void whenGetStudentsByAgeBetween_ThenGetArrayIfMinAndMaxIsNotNull() throws Exception {
        mockMvc.perform(get("/student?min=25&max=34&pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void whenGetStudentsBy_ThenReturnStatusBadRequestIsMinAndMaxIsNull() throws Exception {
        mockMvc.perform(get("/student?min=null&max=null&pageNumber=1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetStudentsBy_ThenReturnStatusBadRequestIsMinIsNullAndMaxIsNull() throws Exception {
        mockMvc.perform(get("/student?min=null&max=34&pageNumber=1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findFacultyByStudentId() throws Exception {
        mockMvc.perform(get("/student/" + student.getId() + "/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }

    @Test
    void testGetCountStudents() throws Exception {
        mockMvc.perform(get("/student/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    void getMiddleAge() throws Exception {
        mockMvc.perform(get("/student/middle-age"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    void getYoungest() throws Exception {
        mockMvc.perform(get("/student/youngest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}