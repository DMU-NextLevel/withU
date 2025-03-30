package NextLevel.demo.oauth;

import NextLevel.demo.user.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginService loginService;

    public OAuthSuccessHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        OAuth2User OAuthUser = (OAuth2User) authentication.getPrincipal();

        loginService.socialLogin(OAuthUser.getDto(), response);

        log.info("social login success");
    }
}
