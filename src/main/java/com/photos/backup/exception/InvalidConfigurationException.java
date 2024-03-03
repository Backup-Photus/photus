package com.photos.backup.exception;

import java.io.IOException;

public class InvalidConfigurationException extends IOException {
    public InvalidConfigurationException(String message){
        super(message);
    }
}
