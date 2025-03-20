package NextLevel.demo.service;

import NextLevel.demo.dto.UserDto.RequestUserCreateDto;
import NextLevel.demo.entity.UserDetailEntity;
import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.repository.UserDetailRepository;
import java.util.Map;
import java.util.Optional;
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

    private final UserDetailRepository userDetailRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        String social_provider = userRequest.getClientRegistration().getRegistrationId();

        log.info("social_provider=%s", social_provider);
        attributes.keySet().forEach(key -> log.info("%s : %s", key, attributes.get(key)));

        userDetailRepository.findBySocialId("id");

        return new PrincipalDetails
    }

    private RequestUserCreateDto getInfoFromSocialProvider(String socialProvider, Map<String, Object> attributes) {
        RequestUserCreateDto.RequestUserCreateDtoBuilder builder = RequestUserCreateDto.builder()
            .socialProvider(socialProvider);

        switch (socialProvider) {
            case "google":
                return builder
                    .socialId()
                    .address()
                    .email()
                    .number()
                    .build();

            case "naver":
                return builder
                    .socialId()
                    .address()
                    .email()
                    .number()
                    .build();

            case "kakao":
                return builder
                    .socialId()
                    .address()
                    .email()
                    .number()
                    .build();

            default:
                throw new CustomException(ErrorCode.SIBAL_WHAT_IS_IT, String.format("SocialLoginService::getInfoFromSocialProvider::input strange social provider : %s",socialProvider));
        }
    }
}
