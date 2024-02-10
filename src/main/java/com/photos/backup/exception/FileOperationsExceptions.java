package com.photos.backup.exception;

import com.photos.backup.constants.FileOperationConstants;

public class FileOperationsExceptions extends  RuntimeException{

    public FileOperationsExceptions(FileOperationConstants constants){
        super(constants.getMessage());
    }
}
