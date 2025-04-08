package NextLevel.demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // no authentication : 공통
    NEED_ADDITIONAL_DATA(HttpStatus.FORBIDDEN, "Additional data required"),
    NO_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "No authentication please login"),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST, "작성자가 아닙니다"),

    //register
    ALREADY_EXISTS_EMAIL(HttpStatus.CONFLICT, "The email already exists"),

    // login
    LOGIN_FAILED(HttpStatus.CONFLICT, "Login failed"),

    // img 관련 error
    ERROR_ON_SAVE_IMG(HttpStatus.INTERNAL_SERVER_ERROR, "error on save img"),
    ERROR_ON_DELETE_IMG(HttpStatus.INTERNAL_SERVER_ERROR, "error on delete img"),

    // project
    NOT_CORRECT_TAG_SIZE(HttpStatus.CONFLICT, "invalidated tag input") ,
    NOT_FOUND_PROJECT(HttpStatus.NOT_FOUND, "Not found project : %s") ,

    // 시발 이게 뭐지? error
    SIBAL_WHAT_IS_IT(HttpStatus.INTERNAL_SERVER_ERROR, "알지 모르는 error 발생 : %s");

    public HttpStatus errorCode;
    public String errorMessage;

    ErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode; this.errorMessage = errorMessage;
    }
}
