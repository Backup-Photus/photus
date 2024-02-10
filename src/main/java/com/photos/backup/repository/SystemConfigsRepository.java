package com.photos.backup.repository;

import lombok.Getter;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
public class SystemConfigsRepository {
    private final String dataDirPath;
    private final boolean dataDirExist;
    private final boolean hasReadPermission;
    private final boolean hasWritePermission;
    private final int port;
    private final String baseUrl;

    public SystemConfigsRepository(String dataDirPath,String port,String baseUrl){
        this.port= Integer.parseInt(port);
        this.baseUrl=baseUrl;
        this.dataDirPath=dataDirPath;
        dataDirExist=checkForDataDirExist();
        hasReadPermission = checkForReadPermissions();
        hasWritePermission =  checkForWritePermissions();
    }


    private boolean checkForWritePermissions() {
        File directory =  new File(dataDirPath);
        return dataDirExist && directory.canWrite();
    }

    private boolean checkForDataDirExist(){
        File directory = new File(dataDirPath);
        return directory.exists() && directory.isDirectory();
    }
    private boolean checkForReadPermissions(){
        File directory = new File(dataDirPath);
        return  dataDirExist && directory.canRead();
    }

    public String getTodayDate(){
        LocalDate today=LocalDate.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTimeFormatter.format(today);
    }

    public UUID getNewID(){
        return UUID.randomUUID();
    }

    public String getBaseUrlWithPort(){
        return baseUrl+":"+port;
    }

}
