package NextLevel.demo.model;

import NextLevel.demo.role.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetail {
    private Long id;
    private String UUID;
    private UserRole role;

    private String email;
    private String password;
    private String socialProvider;
    private String socialId;

    @Builder
    public UserDetail(Long id, String uuid, UserRole userRole, String password,
        String socialProvider, String socialId, String email) {
        this.id = id;
        this.UUID = uuid;
        this.role = userRole;
        this.password = password;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.email = email;
    }

    // made by login : user table에 먼저 넣고 해당 id값으로 동일한게 userDtail도 설정하기 위함
    public void setId(Long id) {this.id = id;}

    // db에서 참조시 사용됨
    public String getRole() {
        return role.name();
    }
    public void setRole(String roleName) {
        this.role = UserRole.getRole(roleName);
    }
}
