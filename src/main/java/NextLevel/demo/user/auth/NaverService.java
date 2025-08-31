package NextLevel.demo.user.auth;

import NextLevel.demo.img.service.ImgServiceImpl;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.service.LoginService;
import java.util.Map;
import java.util.UUID;
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
public class NaverService {

    private final LoginService loginService;
    private final ImgServiceImpl imgService;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String AccessTokenURI;
    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String UserInfoURI;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String RedirectURI;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String ClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String ClientSecret;

    public UserDetailEntity login(String code) {
        Map<String, String> info = getUserFromNaver(code);

        return loginService.socialLogin(
            RequestUserCreateDto
                .builder()
                .socialProvider("naver")
                .socialId(info.get("id"))
                .email(info.get("email"))
                .name(info.get("name"))
                .number(info.get("mobile"))
                .nickName(info.get("nickname"))
                .imgEntity(imgService.saveSocialImg(info.get("profile_image")))
                .build()
        );
    }

    public Map<String, String> getUserFromNaver(String code) {
        Map<String, Object> naverToken = WebClient.create(AccessTokenURI).post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", ClientId)
                .queryParam("client_secret", ClientSecret)
                .queryParam("code", code)
                .queryParam("state", UUID.randomUUID().toString())
                .build(true))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(Map.class)
            .block();

        return getUserInfo( (String) naverToken.get("access_token"));
    }

    public Map<String, String> getUserInfo(String accessToken) {
        return WebClient.create(UserInfoURI).post()
            .uri(uriBuilder -> uriBuilder
                .build(true))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(Map.class)
            .map(response -> {
                return (Map<String, String>) response.get("response");
            })
            .block();
    }
}
