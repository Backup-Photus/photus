package com.photos.backup.config;

import com.photos.backup.dto.ErrorDTO;
import com.photos.backup.dto.ResponseDTO;
import com.photos.backup.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Object>> handleException(Exception e) {
        ErrorDTO errorResponse =  ErrorDTO.builder()
                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
        return new ResponseEntity<>(errorResponse.toResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Object>> handleMethodValidationExceptions(MethodArgumentNotValidException validException){
        ErrorDTO errorDTO =  ErrorDTO.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(validException.getBody().getDetail())
                .error(String.valueOf(validException.getErrorCount()))
                .build();
        return new ResponseEntity<>(errorDTO.toResponseDTO(),validException.getStatusCode());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseDTO<Object>> handleUsersExceptions(ApplicationException exception) {
        return new ResponseEntity<>(exception.toErrorResponse().toResponseDTO(), HttpStatus.BAD_REQUEST);
    }
}
