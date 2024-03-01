package com.photos.backup.exception;

import com.photos.backup.pojo.ApplicationErrors;
import com.photos.backup.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PhotosException extends  ApplicationException{

    public PhotosExceptions exceptions;
    public String id;

    @Override
    public ErrorDTO toErrorResponse() {
        return ErrorDTO.builder()
                .error(exceptions.getMessage())
                .errorCode(exceptions.getValue())
                .message(exceptions.getMessage())
                .build();
    }

    @Getter
    @AllArgsConstructor
    public enum PhotosExceptions implements ApplicationErrors {
        PHOTO_NOT_FOUND(100201),
        NOT_AUTHORISED_TO_ACCESS(100202),
        PHOTO_TOO_LARGE_TO_READ(100203),
        PHOTO_TOO_LARGE_TO_WRITE(100204),
        PHOTO_NO_METADATA_AVAILABLE(100205);
        private final int value;
        private final String message;

        PhotosExceptions(int value){
            this.value=value;
            this.message=this.name();
        }
    }
}

