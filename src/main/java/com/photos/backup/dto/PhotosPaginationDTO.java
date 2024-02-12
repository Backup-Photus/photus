package com.photos.backup.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record PhotosPaginationDTO<T>(
        Long nextPage,
        long currentPage,
        boolean isEndPage,
        String nextPageLink,
        List<T> data,
        int numberOfElements,
        int pageSize
) {

}

