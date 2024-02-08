package com.photos.backup.exception;

public class PhotoNotFoundException extends  RuntimeException{

    public PhotoNotFoundException(String id)
    {
        super("Photo with id "+id+" not found in database");
    }
}
