package ru.hogwarts.schoolHW.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.hogwarts.schoolHW.config.TestConfig;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.repository.FacultyRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class HouseControllerTest extends TestConfig {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    MockMvc mockMvcFaculty;
    private Faculty faculty;
    private JSONObject jsonObjectFaculty;

    @BeforeEach
    void setUp() throws JSONException {
        faculty = new Faculty();
        faculty.setColor("Green");
        faculty.setName("Alchemy");
        facultyRepository.save(faculty);

        jsonObjectFaculty = new JSONObject();
        jsonObjectFaculty.put("name", "Cast");
        jsonObjectFaculty.put("color", "black");
    }
    @AfterEach
    void tearDown(){
        facultyRepository.deleteAll();
    }

    @Test
    void whenFindFacultyById_ThenGetFaculty() throws Exception {
        mockMvcFaculty.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value("Alchemy"));
    }

    @Test
    void whenFindFacultyById_ThenStatusNotFoundIfFacultyIsNotFound() throws Exception {
        facultyRepository.delete(faculty);
        mockMvcFaculty.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFacultyAdded_thenItExistsInList() throws Exception {
        mockMvcFaculty.perform(post("/faculty/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObjectFaculty.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Cast"));

        Faculty faculty1 = facultyRepository.findByNameIgnoreCase("cast").get(0);
        mockMvcFaculty.perform(get("/faculty/" + faculty1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cast"));
    }

    @Test
    void testEditFaculty() throws Exception {
        jsonObjectFaculty.put("name", "Curse");
        mockMvcFaculty.perform(put("/faculty/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObjectFaculty.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Curse"));
    }

    @Test
    void whenEditFaculty_ReturnStatusBadRequestIfFacultyParameterIsNull() throws Exception {
        mockMvcFaculty.perform(put("/faculty/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void wheneEditFaculty_ReturnStatusBadRequestIfFacultyNotFound() throws Exception {
        facultyRepository.delete(faculty);

        mockMvcFaculty.perform(put("/faculty/edit"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRemoveFaculty() throws Exception {
        mockMvcFaculty.perform(delete("/faculty/remove/" + faculty.getId()))
                .andExpect(status().isOk());

        mockMvcFaculty.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetFacultyByColor_ThenStatus200IfNameAndColorIsNotNull() throws Exception {
        mockMvcFaculty.perform(get("/faculty?color=" + faculty.getColor() + "&name=" + faculty.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }

    @Test
    void whenGetFacultyByColor_ThenStatus200IfNameIsNotNull() throws Exception {
        mockMvcFaculty.perform(get("/faculty?color=null" + "&name=" + faculty.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()));
    }

    @Test
    void whenGetFacultyByColor_ThenStatus200IfColorIsNotNull() throws Exception {
        mockMvcFaculty.perform(get("/faculty?color=" + faculty.getColor() + "&name=null"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }

    @Test
    void whenGetFacultyByColor_ThenStatus200IfNameAndColorIsNull() throws Exception {
        mockMvcFaculty.perform(get("/faculty?color=null&name=null"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentsByFacultyId() throws Exception {
        mockMvcFaculty.perform(get("/faculty/" + faculty.getId() + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getStudentsByFacultyId_ifFacultyIsNotFound() throws Exception {
        facultyRepository.delete(faculty);
        mockMvcFaculty.perform(get("/faculty/" + faculty.getId() + "/students"))
                .andExpect(status().isBadRequest());
    }
}