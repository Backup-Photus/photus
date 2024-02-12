package com.photos.backup.config;

import com.photos.backup.exception.ApplicationException;
import com.photos.backup.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception e) {
        ErrorDTO errorResponse =  ErrorDTO.builder()
                .error(e.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodValidationExceptions(MethodArgumentNotValidException validException){
        ErrorDTO errorDTO =  ErrorDTO.builder()
                .errorCode(validException.getStatusCode().value())
                .message(validException.getBody().getDetail())
                .error(String.valueOf(validException.getErrorCount()))
                .build();
        return new ResponseEntity<>(errorDTO,validException.getStatusCode());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDTO> handleUsersExceptions(ApplicationException exception) {
        return new ResponseEntity<>(exception.toErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
