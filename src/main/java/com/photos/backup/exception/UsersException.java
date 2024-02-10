package com.photos.backup.exception;

import com.photos.backup.pojo.ApplicationErrors;
import com.photos.backup.pojo.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UsersException extends ApplicationException{
    public  UserExceptions exception;
    public String userId;

    @Override
    public ErrorResponse toErrorResponse() {
        return new ErrorResponse.Builder()
                .error(exception.getMessage())
                .errorCode(exception.getValue())
                .messages(exception.getMessage())
                .build();
    }

    @Getter
    @AllArgsConstructor
    public enum UserExceptions  implements ApplicationErrors{
        USER_NOT_FOUND(100301),
        USER_NOT_AUTHORIZES(100302);

        private final int value;
        private final String message;

        UserExceptions(int value){
            this.value=value;
            this.message=this.name();
        }
    }

}
