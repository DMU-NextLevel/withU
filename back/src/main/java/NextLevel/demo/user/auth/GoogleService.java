package NextLevel.demo.user.auth;

import NextLevel.demo.img.service.ImgServiceImpl;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.service.LoginService;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleService {

    private final LoginService loginService;
    private final ImgServiceImpl imgService;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String AccessTokenURI;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String UserInfoURI;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String RedirectURI;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String ClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String ClientSecret;

    public UserDetailEntity getUserFromGoogle(String code) {
        String accessToken = getAccessTokenFromGoogle(code);

        Map<String, String> info = getUserInfoFromGoogle(accessToken);

        return loginService.socialLogin(
            RequestUserCreateDto
                .builder()
                .socialProvider("google")
                .socialId(info.get("id"))
                .email(info.get("email"))
                .name(info.get("name"))
                .nickName(info.get("given_name"))
                .imgEntity(imgService.saveSocialImg(info.get("picture")))
                .build()
        );
    }

    private String getAccessTokenFromGoogle(String code) {
        Map<String, Object> googleTokenResponse = WebClient.create(AccessTokenURI).post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("client_id", ClientId)
                .queryParam("client_secret", ClientSecret)
                .queryParam("code", code)
                .queryParam("redirect_uri", RedirectURI)
                .queryParam("grant_type", "authorization_code")
                .build())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header(HttpHeaders.CONTENT_LENGTH, "0")
            //.contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .block();

        return (String) googleTokenResponse.get("access_token");
    }

    private Map<String, String> getUserInfoFromGoogle(String accessToken) {
        return WebClient.create(UserInfoURI).get()
            .uri(uriBuilder -> uriBuilder
                .build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (response) -> Mono.error(new RuntimeException(response.toString())))
            .bodyToMono(Map.class)
            .block();
    }
}