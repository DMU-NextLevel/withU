package NextLevel.demo.user.dto.user.response;

import NextLevel.demo.img.ImgDto;
import NextLevel.demo.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class UserProfileDto {

    private Long id;
    private String nickName;
    private ImgDto img;

    public static UserProfileDto of(UserEntity user) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.id = user.getId();
        userProfileDto.nickName = user.getNickName();
        userProfileDto.img = new ImgDto(user.getImg());
        return userProfileDto;
    }
}
