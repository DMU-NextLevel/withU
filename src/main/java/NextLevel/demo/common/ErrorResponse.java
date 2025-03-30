package NextLevel.demo.common;

import NextLevel.demo.exception.ErrorCode;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private String message;
    private HttpStatus httpStatus;

    public ErrorResponse(ErrorCode errorCode, String... args) {
        message = String.format(errorCode.errorMessage, args);
        httpStatus = errorCode.errorCode;
    }

}
