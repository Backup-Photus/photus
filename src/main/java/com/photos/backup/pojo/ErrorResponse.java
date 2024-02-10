package com.photos.backup.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    int errorCode;
    String error;
    String messages;

    private ErrorResponse(Builder builder) {
        setErrorCode(builder.errorCode);
        setError(builder.error);
        setMessages(builder.messages);
    }

    public static final class Builder {
        private int errorCode;
        private String error;
        private String messages;

        public Builder() {
        }

        public Builder errorCode(int val) {
            errorCode = val;
            return this;
        }

        public Builder error(String val) {
            error = val;
            return this;
        }

        public Builder messages(String val) {
            messages = val;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
