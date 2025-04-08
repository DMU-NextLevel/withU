package NextLevel.demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // no authentication : 공통
    NEED_ADDITIONAL_DATA(HttpStatus.FORBIDDEN, "00403","Additional data required"),
    NO_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "00401","No authentication please login"),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST, "10001","작성자가 아닙니다"),

    //register
    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "01001","email already exists"),

    // login
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "02001","Login failed"),

    // img 관련 error
    ERROR_ON_SAVE_IMG(HttpStatus.CONFLICT, "03001","error on save img"),
    ERROR_ON_DELETE_IMG(HttpStatus.CONFLICT, "03002","error on delete img"),

    // project
    NOT_CORRECT_TAG_SIZE(HttpStatus.BAD_REQUEST, "04001","invalidated tag input") ,
    NOT_FOUND_PROJECT(HttpStatus.BAD_REQUEST, "04002","Not found project : %s") ,
    ERROR_EXPIRED_DATE_CONVERSION(HttpStatus.BAD_REQUEST, "04003","can not convert expired : %s"),
    INVALIDATE_TYPE(HttpStatus.BAD_REQUEST, "04004","invalidated type %s"),

    // 시발 이게 뭐지? error
    SIBAL_WHAT_IS_IT(HttpStatus.INTERNAL_SERVER_ERROR, "05001","알지 모르는 error 발생 : %s");

    public HttpStatus statusCode;
    public String CustomErrorCode;
    public String errorMessage;

    ErrorCode(HttpStatus statusCode, String CustomErrorCode, String errorMessage) {
        this.statusCode = statusCode; this.errorMessage = errorMessage; this.CustomErrorCode = CustomErrorCode;
    }
}
