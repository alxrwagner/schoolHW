package ru.hogwarts.schoolHW.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.schoolHW.dto.AppInfoDTO;
import ru.hogwarts.schoolHW.model.AppInfo;

@RestController
public class InfoController {

    @Value("${app.env}")
    private String env;
    @Value("${app.version}")
    private String version;
    @Value("${app.name}")
    private String name;

    @GetMapping("/appinfo")
    public AppInfoDTO getAppInfo(){
        AppInfo appInfo = new AppInfo(version, name, env);
        return AppInfoDTO.fromAppInfo(appInfo);
    }
}
