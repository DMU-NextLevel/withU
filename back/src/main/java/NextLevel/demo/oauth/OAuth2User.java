package NextLevel.demo.oauth;

import NextLevel.demo.user.dto.RequestUserCreateDto;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;

public class OAuth2User implements
    org.springframework.security.oauth2.core.user.OAuth2User {

    private RequestUserCreateDto dto;

    public OAuth2User(RequestUserCreateDto dto) {
        this.dto = dto;
    }

    public RequestUserCreateDto getDto() {
        return dto;
    }


    // not used
    @Override
    public <A> A getAttribute(String name) {
        return null;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public String getName() {
        return "1";
    }
}
