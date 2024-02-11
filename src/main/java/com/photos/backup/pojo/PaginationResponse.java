package com.photos.backup.pojo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PaginationResponse<T> {
    private Long nextPage;
    private long currentPage;
    private boolean isEndPage;
    private String nextPageLink;
    private List<T> data;
    private int numberOfElements;
    private int pageSize;
}

