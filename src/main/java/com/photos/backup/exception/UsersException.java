package com.photos.backup.exception;

import com.photos.backup.pojo.ApplicationErrors;
import com.photos.backup.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UsersException extends ApplicationException{
    public  UserExceptions exception;
    public String userId;

    @Override
    public ErrorDTO toErrorResponse() {
        return ErrorDTO.builder()
                .error(exception.getMessage())
                .errorCode(exception.getValue())
                .message(exception.getMessage())
                .build();
    }

    @Getter
    @AllArgsConstructor
    public enum UserExceptions  implements ApplicationErrors{
        USER_NOT_FOUND(100301),
        USER_NOT_AUTHORIZES(100302),
        INVALID_CREDENTIALS(100303),
        USER_ALREADY_EXIST(100304),
        USER_EMAIL_ALREADY_IN_USER(100305);

        private final int value;
        private final String message;

        UserExceptions(int value){
            this.value=value;
            this.message=this.name();
        }
    }

}
