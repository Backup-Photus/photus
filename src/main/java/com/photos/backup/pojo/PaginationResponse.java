package com.photos.backup.pojo;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationResponse<T> {
    private Long nextPage;
    private long currentPage;
    private boolean isEndPage;
    private String nextPageLink;
    private List<T> data;
    private int numberOfElements;
    private int pageSize;

    private PaginationResponse(Builder<T> builder) {
        this.nextPage = builder.nextPage;
        this.currentPage = builder.currentPage;
        this.isEndPage = builder.isEndPage;
        this.nextPageLink = builder.nextPageLink;
        this.data = builder.data;
        this.numberOfElements=builder.data.size();
        this.pageSize=builder.pageSize;
    }

    // Getters...

    public static class Builder<T> {
        private Long nextPage;
        private long currentPage;
        private boolean isEndPage;
        private String nextPageLink;
        private List<T> data;

        private int pageSize;
        public Builder<T> setNextPage(long nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        public Builder<T> setCurrentPage(long currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Builder<T> setIsEndPage(boolean isEndPage) {
            this.isEndPage = isEndPage;
            return this;
        }

        public Builder<T> setNextPageLink(String nextPageLink) {
            this.nextPageLink = nextPageLink;
            return this;
        }

        public Builder<T> setData(List<T> data) {
            this.data = data;
            return this;
        }

        public Builder<T> setPageSize(int pageSize) {
            this.pageSize=pageSize;
            return this;
        }

        public PaginationResponse<T> build() {
            return new PaginationResponse<>(this);
        }
    }
}

