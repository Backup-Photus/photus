package com.photos.backup.exception;

import com.photos.backup.pojo.ErrorResponse;

public abstract class ApplicationException extends RuntimeException{
    public abstract ErrorResponse toErrorResponse();
}
