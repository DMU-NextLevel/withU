package NextLevel.demo.user.dto.user.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
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
