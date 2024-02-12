package com.photos.backup.pojo;

import com.photos.backup.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorFreeDTO<T> {
     public static ErrorFreeDTO<?> EmptyErrorFreeDTO = new ErrorFreeDTO<>(null);
    private T result;
    private ErrorDTO error;
    public ErrorFreeDTO(T data){
        this.result =data;
        this.error =ErrorDTO.builder()
                .hasError(false)
                .error("No Error")
                .message("No Error")
                .errorCode(-1)
                .build();

    }

}