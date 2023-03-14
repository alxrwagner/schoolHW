package ru.hogwarts.schoolHW.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schoolHW.dto.AvatarDTO;
import ru.hogwarts.schoolHW.service.AvatarService;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/student/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/student/{id}/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        AvatarDTO avatarDTO = avatarService.findByStudentId(id);

        if (avatarDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatarDTO.getMediaType()));
        headers.setContentLength(avatarDTO.getFileSize());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatarDTO.getData());
    }

    @GetMapping(value = "/student/{id}")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        AvatarDTO avatarDTO = avatarService.findByStudentId(id);

        Path path = Path.of(avatarDTO.getFilePath());

        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream()){
            response.setStatus(200);
            response.setContentType(avatarDTO.getMediaType());
            response.setContentLength((int) avatarDTO.getFileSize());
            is.transferTo(os);
        }
    }
}
