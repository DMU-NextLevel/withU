package NextLevel.demo.user.auth;

import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.service.LoginService;
import io.netty.handler.codec.http.HttpHeaderValues;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final LoginService loginService;
    private final ImgService imgService;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String AccessTokenURI;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String UserInfoURI;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String RedirectURI;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String ClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String ClientSecret;

    public UserDetailEntity login(String code) {
        Map<String, Object> info = getUserFromKakao(code);

        return loginService.socialLogin(
            RequestUserCreateDto
                .builder()
                .socialProvider("kakao")
                .socialId( String.valueOf(info.get("id")))
                .nickName( (String) ((Map<String, Object>)info.get("properties")).get("nickname") )
                .name((String) ((Map<String, Object>)info.get("kakao_account")).get("name"))
                .number((String) ((Map<String, Object>)info.get("kakao_account")).get("phone"))
                .email( (String) ((Map<String, Object>)info.get("kakao_account")).get("email") )
                .imgEntity(imgService.saveSocialImg( (String) ((Map<String, Object>)info.get("properties")).get("profile_image") ) )
                .build()
        );
    }

    public Map<String, Object> getUserFromKakao(String code) {
        Map<String, Object> kakaoToken = WebClient.create(AccessTokenURI).post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", ClientId)
                .queryParam("code", code)
                .queryParam("client_secret", ClientSecret)
                .build(true))
            .header(HttpHeaders.CONTENT_TYPE,
                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(Map.class)
            .block();

        return getUserInfo( (String) kakaoToken.get("access_token"));
    }

    public Map<String, Object> getUserInfo(String accessToken) {

        return WebClient.create(UserInfoURI)
            .get()
            .uri(uriBuilder -> uriBuilder
                .build(true))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
            .bodyToMono(Map.class)
            .block();
    }
}
