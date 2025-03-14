package NextLevel.demo.model;

import NextLevel.demo.role.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetail {
    private Long id;
    private String UUID;
    private UserRole role;

    private String password;
    private String socialProvider;
    private String socialPk;

    @Builder
    public UserDetail(Long id, String uuid, UserRole userRole, String password,
        String socialProvider,
        String socialPk) {
        this.id = id;
        this.UUID = uuid;
        this.role = userRole;
        this.password = password;
        this.socialProvider = socialProvider;
        this.socialPk = socialPk;
    }

    public String getRole() {
        return role.name();
    }
}
