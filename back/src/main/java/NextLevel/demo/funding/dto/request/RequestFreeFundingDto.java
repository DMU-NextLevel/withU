package NextLevel.demo.funding.dto.request;

import NextLevel.demo.funding.entity.FreeFundingEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestFreeFundingDto {

    private Long freePrice;
    private Long projectId;

    private Long userId;

    public FreeFundingEntity toEntity(UserEntity user, ProjectEntity project) {
        return FreeFundingEntity
                .builder()
                .user(user)
                .project(project)
                .price(freePrice)
                .build();
    }

}
