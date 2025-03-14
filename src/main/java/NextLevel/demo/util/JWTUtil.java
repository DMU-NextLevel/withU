package NextLevel.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

public class JWTUtil {

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.access-token-time}")
    public static Long ACCESS_TOKEN_TIME;
    @Value("${jwt.refresh-token-time}")
    public static Long REFRESH_TOKEN_TIME;

    public static final String ACCESS_TOKEN = "access";
    public static final String REFRESH_TOKEN = "refresh";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String makeToken(String userId, Map<String, String> claims, Long time) {
        JwtBuilder builder = Jwts.builder().setSubject(userId);

        claims.forEach((k, v) -> builder.claim(k, v));
        return builder
            .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public Map<String, String> decodeToken(String token, String... requiredClaims) {
        HashMap<String, String> claims = new HashMap<>();

        Claims body = Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

        claims.put("userId", body.getSubject());

        for (String arg : requiredClaims) {
            claims.put(arg, body.get(arg, String.class));
        }

        return claims;
    }
}
