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
    public SystemConfigsRepository(String dataDirPath){
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

}
