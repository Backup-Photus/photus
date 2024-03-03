package com.photos.backup.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private  final String extractorMicroservice;

    public SystemConfigsRepository(String dataDirPath,String extractorMicroservice){
        this.extractorMicroservice=extractorMicroservice;
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

    @JsonIgnore
    public String getTodayDate(){
        LocalDate today=LocalDate.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTimeFormatter.format(today);
    }

    @JsonIgnore
    public UUID getNewID(){
        return UUID.randomUUID();
    }

}
