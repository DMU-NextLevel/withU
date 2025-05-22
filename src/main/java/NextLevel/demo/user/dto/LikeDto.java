package NextLevel.demo.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class LikeDto {

    private Long userId;
    @NotNull
    private Long projectId;

    @NotNull
    private Boolean like;
}
