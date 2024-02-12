package com.photos.backup.exception;

import com.photos.backup.dto.ErrorDTO;

public abstract class ApplicationException extends RuntimeException{
    public abstract ErrorDTO toErrorResponse();
}
