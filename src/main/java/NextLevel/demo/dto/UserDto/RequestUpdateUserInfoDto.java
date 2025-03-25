package NextLevel.demo.dto.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RequestUpdateUserInfoDto {

    private Long userId;

    private String name;
    private String nickName;

    private String email;
    private String address;
    private String phone;

    private String img;

}
