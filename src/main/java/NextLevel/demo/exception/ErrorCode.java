package NextLevel.demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // login
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "Login failed"),

    // 시발 이게 뭐지? error
    SIBAL_WHAT_IS_IT(HttpStatus.INTERNAL_SERVER_ERROR, "알지 모르는 error 발생 : %s"),

    ;
    public HttpStatus errorCode;
    public String errorMessage;

    ErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode; this.errorMessage = errorMessage;
    }
}
