package NextLevel.demo.oauth;

import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.user.repository.UserDetailRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginService extends DefaultOAuth2UserService {

    private final ImgService imgService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        String social_provider = userRequest.getClientRegistration().getRegistrationId();

        RequestUserCreateDto requestDto = getInfoFromSocialProvider(social_provider, attributes);

        return new NextLevel.demo.oauth.OAuth2User(requestDto);
    }

    private RequestUserCreateDto getInfoFromSocialProvider(String socialProvider, Map<String, Object> attributes) {
        RequestUserCreateDto.RequestUserCreateDtoBuilder builder = RequestUserCreateDto.builder()
            .socialProvider(socialProvider);

        switch (socialProvider) {
            case "google":
                return builder
                    .socialId((String)attributes.get("id"))
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("name"))
                    .nickName((String) attributes.get("given_name"))
                    .imgEntity(imgService.saveSocialImg((String)attributes.get("picture")))
                    .build();

            case "naver":
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                return builder
                    .socialId((String) response.get("id"))
                    .name((String) response.get("name"))
                    .nickName((String) response.get("nickname"))
                    .email((String) response.get("email"))
                    .number((String) response.get("mobile"))
                    .imgEntity(imgService.saveSocialImg((String) response.get("profile_image")))
                    .build();

            case "kakao":
                return builder
                    .socialId(String.valueOf(attributes.get("id")))
                    .email((String) ((Map<String, Object>) attributes.get("kakao_account")).get("email"))
                    .nickName((String) ((Map<String, Object>)attributes.get("properties")).get("nickname"))
                    .imgEntity(imgService.saveSocialImg((String) ((Map<String, Object>)attributes.get("properties")).get("profile_image")))
                    .build();

            default:
                throw new CustomException(ErrorCode.SIBAL_WHAT_IS_IT, String.format("SocialLoginService::getInfoFromSocialProvider::input strange social provider : %s",socialProvider));
        }
    }
}
