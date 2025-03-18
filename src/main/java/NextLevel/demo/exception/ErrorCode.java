package NextLevel.demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ;

    public HttpStatus errorCode;
    public String errorMessage;

    ErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode; this.errorMessage = errorMessage;
    }
}
