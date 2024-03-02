package com.photos.backup.dto;

import lombok.Builder;


@Builder
public record ErrorDTO (
    int errorCode,
    String error,
    String message,
    Boolean hasError
)  {

    @Override
    public Boolean hasError() {
        return hasError == null;
    }

    public ResponseDTO<Object> toResponseDTO(){
        return ResponseDTO.builder()
                .result(null)
                .error(this)
                .build();
    }
}
