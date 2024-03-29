package com.photos.backup.exception;

import com.photos.backup.pojo.ApplicationErrors;
import com.photos.backup.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FileException extends ApplicationException {
    public FileOperationExceptions exception;

    @Override
    public ErrorDTO toErrorResponse() {
        return ErrorDTO.builder()
                .error(exception.getMessage())
                .errorCode(exception.getValue())
                .message(exception.getMessage())
                .build();
    }


    @Getter
    @AllArgsConstructor
    public enum FileOperationExceptions implements ApplicationErrors {
        NO_FREE_SPACE(100100,"NO_FREE_SPACE"),
        NO_WRITE_ACCESS_IN_DATA_DIR(100101,"NO_WRITE_ACCESS_IN_DATA_DIR"),
        NO_READ_ACCESS_IN_DATA_DIR(100102,"NO_READ_ACCESS_IN_DATA_DIR"),
        NO_FILE_EXIST(100103,"NO_FILE_EXIST"),
        FILE_TOO_LARGE(100104,"FILE_TOO_LARGE"),
        INVALID_FILE_TYPE(100105,"INVALID_FILE_TYPE"),
        UNKNOWN_DIR_EXCEPTION(100106,"UNKNOWN_DIR_EXCEPTION"),
        UNKNOWN_FILE_EXCEPTION(100107,"UNKNOWN_FILE_EXCEPTION"),
        NO_SUCH_FILE_TYPE(100108,"NO_SUCH_FILE_TYPE");

        private final int value;
        private final String message;
    }
}
