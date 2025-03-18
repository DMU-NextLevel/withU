package NextLevel.demo.dto.UserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserCreateDto {
    private String name;
    private String email;
    private String address;
    private String number;

    private String password;
    private String socialProvider;
    private String socialId;

    @Builder
    public RequestUserCreateDto(String name, String email, String address, String number,
        String password, String socialProvider, String socialId) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.number = number;
        this.password = password;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
    }
}
