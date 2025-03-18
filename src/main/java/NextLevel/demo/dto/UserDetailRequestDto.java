package NextLevel.demo.dto;

import NextLevel.demo.role.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailRequestDto {
    private long userId;
    private UserRole role;
    private String email;
    private String password;
    private String socialProvider;
    private String social_id;

    @Builder
    public UserDetailRequestDto(long userId, UserRole role, String email,
        String password,
        String socialProvider, String social_id) {
        this.userId = userId;
        this.role = role;
        this.email = email;
        this.password = password;
        this.socialProvider = socialProvider;
        this.social_id = social_id;
    }
}
