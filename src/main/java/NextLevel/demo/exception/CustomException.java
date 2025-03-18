package NextLevel.demo.exception;

public class CustomException extends RuntimeException {
    public ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message, String... args) {
        super(String.format(message, args).toString());
        this.errorCode = errorCode;
    }
}
