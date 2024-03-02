package com.photos.backup.exception;

import com.photos.backup.dto.ErrorDTO;

public class InvalidParamsException extends ApplicationException{
    @Override
    public ErrorDTO toErrorResponse() {
        return ErrorDTO.builder()
                .message("Pass correct parameters")
                .error("Pass correct parameters")
                .errorCode(400)
                .build();
    }
}
