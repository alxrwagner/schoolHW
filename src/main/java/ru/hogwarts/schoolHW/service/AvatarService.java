package ru.hogwarts.schoolHW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schoolHW.dto.AvatarDTO;
import ru.hogwarts.schoolHW.dto.StudentDTO;
import ru.hogwarts.schoolHW.model.Avatar;
import ru.hogwarts.schoolHW.model.Student;
import ru.hogwarts.schoolHW.repository.AvatarRepository;
import ru.hogwarts.schoolHW.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    @Value("${application.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Transactional
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.findStudent(studentId).toStudent();

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        AvatarDTO avatarDTO = findByStudentId(studentId);

        avatarDTO.setFilePath(filePath.toString());
        avatarDTO.setFileSize(file.getSize());
        avatarDTO.setMediaType(file.getContentType());
        avatarDTO.setData(file.getBytes());
        avatarDTO.setStudentId(student.getId());

        Avatar avatar = avatarDTO.toAvatar();
        avatar.setStudent(student);
        avatarRepository.save(avatar);
    }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Transactional
    public AvatarDTO findByStudentId(Long id) {
        return AvatarDTO.fromAvatar(avatarRepository.findByStudentId(id).orElse(new Avatar()));
    }
}
