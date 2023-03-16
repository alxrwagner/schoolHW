package ru.hogwarts.schoolHW.dto;

import lombok.Data;
import ru.hogwarts.schoolHW.model.AppInfo;

@Data
public class AppInfoDTO {
    private String appName;
    private String appVersion;
    private String appEnvironment;

    public static AppInfoDTO fromAppInfo(AppInfo appInfo){
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        appInfoDTO.setAppName(appInfo.getAppName());
        appInfoDTO.setAppVersion(appInfo.getAppVersion());
        appInfoDTO.setAppEnvironment(appInfo.getAppEnvironment());

        return appInfoDTO;
    }

    public AppInfo toAppInfo(){
        AppInfo appInfo = new AppInfo();
        appInfo.setAppName(this.getAppName());
        appInfo.setAppVersion(this.getAppVersion());
        appInfo.setAppEnvironment(this.getAppEnvironment());

        return appInfo;
    }
}
