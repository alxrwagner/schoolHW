package ru.hogwarts.schoolHW.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo {
    private String appName;
    private String appVersion;
    private String appEnvironment;
//
//    public String ver() {
//        final InputStream mfStream = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
//        Manifest mf = new Manifest();
//        try {
//            mf.read(mfStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Attributes atts = mf.getMainAttributes();
//        return atts.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
//
//    }

}
