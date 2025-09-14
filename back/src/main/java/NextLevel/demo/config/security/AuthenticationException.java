package NextLevel.demo.config.security;

public class AuthenticationException extends
    org.springframework.security.core.AuthenticationException {

    public AuthenticationException(String message) {
        super(message);
    }
}
