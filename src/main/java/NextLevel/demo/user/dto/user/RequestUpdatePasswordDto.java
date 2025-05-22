package NextLevel.demo.user.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestUpdatePasswordDto {
    private Long userId;

    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;

}
