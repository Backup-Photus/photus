package com.photos.backup.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userId){
        super("User with id "+ userId + " does not exist in database");
    }
}
