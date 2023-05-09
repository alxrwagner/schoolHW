package ru.hogwarts.schoolHW.dto;

import lombok.Data;
import ru.hogwarts.schoolHW.model.Avatar;
import ru.hogwarts.schoolHW.model.Faculty;
import ru.hogwarts.schoolHW.model.Student;

import javax.persistence.OneToOne;

@Data
public class AvatarDTO {
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    private Long studentId;

    public static AvatarDTO fromAvatar(Avatar avatar){
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setId(avatar.getId());
        avatarDTO.setFilePath(avatar.getFilePath());
        avatarDTO.setMediaType(avatar.getMediaType());
        avatarDTO.setData(avatar.getData());
        avatarDTO.setFileSize(avatar.getFileSize());

        return avatarDTO;
    }

    public Avatar toAvatar(){
        Avatar avatar = new Avatar();
        avatar.setId(this.getId());
        avatar.setFilePath(this.getFilePath());
        avatar.setMediaType(this.getMediaType());
        avatar.setData(this.getData());
        avatar.setFileSize(this.getFileSize());

        return avatar;
    }
}
