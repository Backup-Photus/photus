package com.photos.backup.config;

import com.photos.backup.exception.ApplicationException;
import com.photos.backup.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception e) {
//        ErrorResponse errorResponse =  new ErrorResponse.Builder()
//                .error(e.getMessage())
//                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .messages(HttpStatus.INTERNAL_SERVER_ERROR.name())
//                .build();
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDTO> handleUsersExceptions(ApplicationException exception) {
        return new ResponseEntity<>(exception.toErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
