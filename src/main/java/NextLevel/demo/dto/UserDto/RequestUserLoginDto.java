package NextLevel.demo.dto.UserDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserLoginDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
