package com.photos.backup.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Builder
public record ResponseDTO<T>(
        T result,
        ErrorDTO error
) {

    public String toJson() throws JsonProcessingException {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return converter.getObjectMapper().writeValueAsString(this);
    }

    static public ResponseDTO<Object> EmptyErrorFreeResponse = noErrorResponse(null);
    static public <T> ResponseDTO<T> noErrorResponse(T result){
        return ResponseDTO.<T>builder()
                .result(result)
                .error(errorFreeDTO)
                .build();
    }


    static private ErrorDTO errorFreeDTO = ErrorDTO.builder()
                .hasError(false)
                .error(null)
                .message(null)
                .errorCode(-1)
                .build();

}
