package com.photos.backup.repository;

import lombok.Getter;

import java.io.File;

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


}
