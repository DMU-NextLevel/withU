package NextLevel.demo.oauth;

import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.service.LoginService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${baseUrl}")
    private String baseUrl;
    private final LoginService loginService;
    private final JWTUtil jwtUtil;

    public OAuthSuccessHandler(LoginService loginService, JWTUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        OAuth2User OAuthUser = (OAuth2User) authentication.getPrincipal();

        UserDetailEntity userDetail = loginService.socialLogin(OAuthUser.getDto(), response);

        jwtUtil.addRefresh(response, userDetail.getUserId(), userDetail.getUUID());
        jwtUtil.addAccess(response, userDetail.getUserId(), request, userDetail.getUser().getRole());

        log.info("social login success");

        response.sendRedirect("https://nextlevel.r-e.kr/");
    }
}
