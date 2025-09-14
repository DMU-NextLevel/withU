package NextLevel.demo.util.jwt;

public class NoTokenException extends RuntimeException {

    public NoTokenException() {
        super("not input token");
    }
}
