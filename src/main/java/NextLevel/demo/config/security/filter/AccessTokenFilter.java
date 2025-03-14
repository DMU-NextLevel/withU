package NextLevel.demo.config.security.filter;

import NextLevel.demo.config.security.AuthenticationException;
import NextLevel.demo.config.security.CustomAuthentication;
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
public class AccessTokenFilter extends CustomTokenFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // super.doFilterInternal(request, response, filterChain);

        Map<String, String> claims = jwtUtil.decodeToken(getTokenFromCookies(JWTUtil.ACCESS_TOKEN, request), "ip", "authorities");
        String ip = claims.get("ip");
        Long userId = Long.valueOf(claims.get("userId"));
        String role = claims.get("role");

        if(!ip.equals(getIpFromRequest(request))) {
            // add to black list
            throw new AuthenticationException(String.format("access token :: invalid ip request:%s token:%s", getIpFromRequest(request), ip));
        }

        SecurityContextHolder.getContext().setAuthentication(new CustomAuthentication(userId, role));

        filterChain.doFilter(request, response);
    }
}
