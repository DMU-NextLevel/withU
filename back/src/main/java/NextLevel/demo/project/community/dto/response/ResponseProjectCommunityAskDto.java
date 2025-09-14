package NextLevel.demo.project.community.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import NextLevel.demo.project.community.entity.ProjectCommunityAskEntity;
import NextLevel.demo.user.dto.user.response.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseProjectCommunityAskDto {

    private Long id;
    private String content;
    private UserProfileDto userProfileDto;
    private LocalDateTime createdAt;

    public static ResponseProjectCommunityAskDto of(ProjectCommunityAskEntity entity) {
        ResponseProjectCommunityAskDto dto = new ResponseProjectCommunityAskDto();
        dto.id = entity.getId();
        dto.createdAt = entity.getCreatedAt();
        dto.userProfileDto = UserProfileDto.of(entity.getUser());
        dto.content = entity.getContent();
        return dto;
    }

}
