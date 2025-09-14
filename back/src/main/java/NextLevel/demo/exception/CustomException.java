package NextLevel.demo.exception;

import NextLevel.demo.config.security.AuthenticationException;

public class CustomException extends AuthenticationException {
    public ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String... args) {
        super(String.format(errorCode.errorMessage, args).toString());
        this.errorCode = errorCode;
    }
}
