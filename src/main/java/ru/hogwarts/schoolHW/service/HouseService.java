package ru.hogwarts.schoolHW.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.schoolHW.dto.FacultyDTO;
import ru.hogwarts.schoolHW.dto.StudentDTO;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.repository.FacultyRepository;
import ru.hogwarts.schoolHW.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private final FacultyRepository facultyRepository;
    static final Logger logger = LoggerFactory.getLogger(FacultyRepository.class);

    public HouseService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Transactional
    public FacultyDTO createFaculty(FacultyDTO facultyDTO){
        logger.info("Was invoke method for create faculty");

        facultyDTO.setId(null);
        Faculty faculty = facultyDTO.toFaculty();
        faculty.setStudents(new ArrayList<>());
        return FacultyDTO.fromFaculty(facultyRepository.save(faculty));
    }

    @Transactional
    public FacultyDTO findFaculty(Long id){
        logger.info("Was invoke method for find faculty by ID");

        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null){
            return FacultyDTO.fromFaculty(faculty);
        }
        return null;
    }

    @Transactional
    public FacultyDTO editFaculty(FacultyDTO facultyDTO){
        logger.info("Was invoke method for edit faculty");

        Faculty faculty = facultyDTO.toFaculty();
       return FacultyDTO.fromFaculty(facultyRepository.save(faculty));
    }

    @Transactional
    public void removeFaculty(Long id){
        logger.info("Was invoke method for delete faculty by ID");

        facultyRepository.deleteById(id);
    }

    public List<FacultyDTO> getFacultyByColor(String color){
        logger.info("Was invoke method for find faculty by color");

        List<Faculty> faculties = facultyRepository.findByColorIgnoreCase(color);
        List<FacultyDTO> facultyDTOs = new ArrayList<>();
        return facultyDTOs = faculties.stream().map(FacultyDTO::fromFaculty).collect(Collectors.toList());
    }

    @Transactional
    public List<FacultyDTO> findByName(String name){
        logger.info("Was invoke method for find faculty by name");

        List<Faculty> faculties = facultyRepository.findByNameIgnoreCase(name);
        List<FacultyDTO> facultyDTOs = new ArrayList<>();
        return facultyDTOs = faculties.stream().map(FacultyDTO::fromFaculty).collect(Collectors.toList());
    }

    @Transactional
    public List<StudentDTO> getStudentsByFacultyId(Long id){
        logger.info("Was invoke method for find students by faculty's ID");

        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null){
            return faculty.getStudents().stream().map(StudentDTO::fromStudent).collect(Collectors.toList());
        }
        return null;
    }
}
