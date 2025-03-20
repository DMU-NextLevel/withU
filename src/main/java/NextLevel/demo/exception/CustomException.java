package NextLevel.demo.exception;

public class CustomException extends RuntimeException {
    public ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String... args) {
        super(String.format(errorCode.errorMessage, args).toString());
        this.errorCode = errorCode;
    }
}
