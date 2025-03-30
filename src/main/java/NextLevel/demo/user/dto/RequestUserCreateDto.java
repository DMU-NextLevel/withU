package NextLevel.demo.user.dto;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.role.UserRole;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class RequestUserCreateDto {
    private Long id;

    private String name;
    private String nickName;
    @NotEmpty
    private String email;
    private String address;
    private String number;

    private String password;
    private String socialProvider;
    private String socialId;

    private MultipartFile img;
    private ImgEntity imgEntity;

    @Builder
    public RequestUserCreateDto(Long id, String name, String nickName, String email, String address, String number,
        String password, String socialProvider, String socialId, ImgEntity imgEntity) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.address = address;
        this.number = number;
        this.password = password;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.imgEntity = imgEntity;
    }

    public UserEntity toUserEntity() {
        return new UserEntity(id, name, nickName, 0, address, number, imgEntity);
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
            ", img='" + img.toString() + '\'' +
            '}';
    }
}
