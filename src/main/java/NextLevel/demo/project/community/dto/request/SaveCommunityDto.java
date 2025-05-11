package NextLevel.demo.project.community.dto.request;

import NextLevel.demo.project.community.entity.ProjectCommunityEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveCommunityDto {

    private Long id;

    private Long userId;
    private Long projectId;

    @NotEmpty
    private String ask;
    private Date askAt;
    private String answer;
    private Date answerAt;

    private ProjectEntity project;
    private UserEntity asker;

    public ProjectCommunityEntity toEntity() {
        return ProjectCommunityEntity.builder()
            .id(id)
            .ask(ask)
            .askAt(askAt)
            .answer(answer)
            .answerAt(answerAt)
            .project(project)
            .asker(asker)
            .build();
    }
}
