package NextLevel.demo.config.security.filter;

import NextLevel.demo.config.security.CustomAuthentication;
import NextLevel.demo.model.UserDetail;
import NextLevel.demo.service.UserService;
import NextLevel.demo.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class RefreshTokenFilter extends CustomTokenFilter {

    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // super.doFilterInternal();

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // decode token
        Map<String, String> claims = jwtUtil.decodeToken(getTokenFromCookies(JWTUtil.REFRESH_TOKEN, request), "uuid");
        Long userId = Long.valueOf(claims.get("userId"));
        String uuid = claims.get("uuid");

        UserDetail dbUser = userService.findUserDetailByUserId(userId);

        // validate
        if(dbUser != null && dbUser.getUUID().equals(uuid)) {
            SecurityContextHolder.getContext().setAuthentication(new CustomAuthentication(userId, dbUser.getRole()));

            // publish new access token

        }

        filterChain.doFilter(request, response);
    }
}
