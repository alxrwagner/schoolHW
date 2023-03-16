package ru.hogwarts.schoolHW.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo {
    private String appName;
    private String appVersion;
    private String appEnvironment;

}
