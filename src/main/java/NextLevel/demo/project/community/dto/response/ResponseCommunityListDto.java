package NextLevel.demo.project.community.dto.response;

import NextLevel.demo.project.community.entity.ProjectCommunityEntity;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Getter
public class ResponseCommunityListDto {
    private int communityCount;
    private List<ResponseProjectCommunityDto> communities;

    public ResponseCommunityListDto (Set<ProjectCommunityEntity> communities) {
        this.communities = communities.stream().map(e->ResponseProjectCommunityDto.of(e)).toList();
        this.communityCount = communities.size();
    }
}

