package NextLevel.demo.util.jwt;

import NextLevel.demo.config.security.CustomAuthentication;
import NextLevel.demo.config.security.filter.CustomTokenFilter;
import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.access-token-time}")
    public int ACCESS_TOKEN_TIME;
    @Value("${jwt.refresh-token-time}")
    public int REFRESH_TOKEN_TIME;
    @Value("${DOMAIN}")
    public String DOMAIN;

    public static final String ACCESS_TOKEN = "access";
    public static final String REFRESH_TOKEN = "refresh";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public void logout(HttpServletResponse response) {
        response.addHeader("Set-Cookie", createCookie(ACCESS_TOKEN, "", 0));
        response.addHeader("Set-Cookie", createCookie(REFRESH_TOKEN, "", 0));
    }

    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response, String newRole) {
        String ip = CustomTokenFilter.getIpFromRequest(request);
        addAccess(response, getUserIdFromSecurityContext(), ip, newRole);
    }

    public static Long getUserIdFromSecurityContext() {

        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userId == null || ! (userId instanceof Long) )
            throw new CustomException(ErrorCode.NO_AUTHENTICATED);

        return (Long)userId;
    }

    public static Long getUserIdFromSecurityContextCanNULL() {

        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userId == null || ! (userId instanceof Long) )
            return null;

        return (Long)userId;
    }

    public void addAccess(HttpServletResponse response, Long userId, String ip, String role) {
        HashMap<String, String> claims = new HashMap<>();
        claims.put("ip", ip);
        claims.put("role", role);
        String token = makeToken(userId.toString(), claims, ACCESS_TOKEN_TIME);

        response.addHeader("Set-Cookie", createCookie(ACCESS_TOKEN, token, ACCESS_TOKEN_TIME));
    }

    public void addAccess(HttpServletResponse response, Long userId, HttpServletRequest request, String role) {
        String ip = CustomTokenFilter.getIpFromRequest(request);
        addAccess(response, userId, ip, role);
    }

    public void addRefresh(HttpServletResponse response, @NotNull Long userId, String uuid) {
        HashMap<String, String> claims = new HashMap<>();
        claims.put("uuid", uuid);

        String token = makeToken(userId.toString(), claims, REFRESH_TOKEN_TIME);

        response.addHeader("Set-Cookie", createCookie(REFRESH_TOKEN, token, REFRESH_TOKEN_TIME));
    }

    public String makeToken(String userId, Map<String, String> claims, int time) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId)
            .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public Map<String, String> decodeToken(String token, String... requiredClaims) {
        if(token == null || token.isEmpty())
            throw new NoTokenException();

        HashMap<String, String> claims = new HashMap<>();

        Claims body;
        try {
            body = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        }catch (Exception e){
            throw new StrangeTokenException();
        }

        claims.put("userId", body.getSubject());

        for (String arg : requiredClaims) {
            claims.put(arg, body.get(arg, String.class));
        }

        return claims;
    }

    private String createCookie(String name, String token, int age){
        ResponseCookie cookie = ResponseCookie.from(name, token)
            .path("/")
            .sameSite("None")
            .httpOnly(true)
            .secure(true)
            .maxAge(age)
            .domain(DOMAIN)
            .build();

        return cookie.toString();
    }
    //response.addHeader("Set-Cookie", createCookie(ACCESS_TOKEN, token, ACCESS_TOKEN_TIME));

}
