package NextLevel.demo.user.dto.user;

import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.entity.UserEntity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class ResponseUserInfoDto {

    private String name;
    private String nickName;
    private int point;
    private String address;
    private String number;
    private String areaNumber;

    private String email;
    private String socialProvider;

    private String img;

    public static ResponseUserInfoDto of(UserEntity userFullEntity) {
        UserDetailEntity detail = userFullEntity.getUserDetail();

        ResponseUserInfoDto dto = new ResponseUserInfoDto(userFullEntity.getName(), userFullEntity.getNickName(), userFullEntity.getPoint(),
            userFullEntity.getAddress(), userFullEntity.getNumber(), userFullEntity.getAreaNumber(), detail.getEmail(),
            detail.getSocialProvider());

        if(userFullEntity.getImg() != null) {
            dto.setImg(userFullEntity.getImg().getUri());
        }

        return dto;
    }

    private ResponseUserInfoDto(String name, String nickName, int point, String address,
        String number, String areaNumber, String email, String socialProvider) {
        this.name = name;
        this.nickName = nickName;
        this.point = point;
        this.address = address;
        this.number = number;
        this.areaNumber = areaNumber;
        this.email = email;
        this.socialProvider = socialProvider;
    }
}
