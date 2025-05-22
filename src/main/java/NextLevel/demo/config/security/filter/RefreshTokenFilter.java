package NextLevel.demo.config.security.filter;

import NextLevel.demo.config.security.CustomAuthentication;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserRepository;
import NextLevel.demo.user.service.LoginService;
import NextLevel.demo.util.jwt.JWTUtil;
import NextLevel.demo.util.jwt.NoTokenException;
import NextLevel.demo.util.jwt.StrangeTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
@Slf4j
public class RefreshTokenFilter extends CustomTokenFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // super.doFilterInternal();

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // decode token
            Map<String, String> claims = jwtUtil.decodeToken(
                getTokenFromCookies(JWTUtil.REFRESH_TOKEN, request), "uuid");
            Long userId = Long.valueOf(claims.get("userId"));
            String uuid = claims.get("uuid");

            Optional<UserEntity> dbUserOpt = userRepository.findUserFullInfoByUserId(userId);
            if(dbUserOpt.isEmpty()) { filterChain.doFilter(request, response); return; }

            UserEntity dbUser = dbUserOpt.get();
            // validate
            if (dbUser != null && dbUser.getUserDetail().getUUID().equals(uuid)) {
                SecurityContextHolder.getContext()
                    .setAuthentication(new CustomAuthentication(userId, dbUser.getRole()));

                jwtUtil.addAccess(response, userId, getIpFromRequest(request), dbUser.getRole());
            }

            log.info("refresh token success");

        } catch (NoTokenException | StrangeTokenException e) {
            log.info("refresh token fail");
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
