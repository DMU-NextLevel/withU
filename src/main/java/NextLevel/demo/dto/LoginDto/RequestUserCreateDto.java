package NextLevel.demo.dto.LoginDto;

import NextLevel.demo.entity.UserDetailEntity;
import NextLevel.demo.entity.UserEntity;
import NextLevel.demo.role.UserRole;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserCreateDto {
    private String name;
    private String nickName;
    @NotEmpty
    private String email;
    private String address;
    private String number;

    private String password;
    private String socialProvider;
    private String socialId;

    private String img;

    @Builder
    public RequestUserCreateDto(String name, String nickName, String email, String address, String number,
        String password, String socialProvider, String socialId, String img) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.address = address;
        this.number = number;
        this.password = password;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.img = img;
    }

    public UserEntity toUserEntity() {
        return new UserEntity(name,nickName, 0, address, number, img);
    }

    public UserDetailEntity toUserDetailEntity(Long id){
        return new UserDetailEntity(id, UUID.randomUUID().toString(), UserRole.USER.name(), email, password, socialProvider, socialId);
    }

    @Override
    public String toString() {
        return "RequestUserCreateDto{" +
            "name='" + name + '\'' +
            ", nickName='" + nickName + '\'' +
            ", email='" + email + '\'' +
            ", address='" + address + '\'' +
            ", number='" + number + '\'' +
            ", password='" + password + '\'' +
            ", socialProvider='" + socialProvider + '\'' +
            ", socialId='" + socialId + '\'' +
            ", img='" + img + '\'' +
            '}';
    }
}
