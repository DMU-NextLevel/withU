package NextLevel.demo.user.dto.user.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestUpdateUserInfoDto {
    private Long id;

    @NotEmpty
    private String name; // column name
    @NotEmpty
    private String value;

}
