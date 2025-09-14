package NextLevel.demo.project.community.dto.response;

import NextLevel.demo.project.community.entity.ProjectCommunityAnswerEntity;
import NextLevel.demo.user.dto.user.response.UserProfileDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class ResponseProjectCommunityAnswerDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserProfileDto userProfileDto;

    public static ResponseProjectCommunityAnswerDto of(ProjectCommunityAnswerEntity entity) {
        ResponseProjectCommunityAnswerDto dto = new ResponseProjectCommunityAnswerDto();
        dto.id = entity.getId();
        dto.content = entity.getContent();
        dto.userProfileDto = UserProfileDto.of(entity.getUser());
        dto.createdAt = entity.getCreatedAt();
        return dto;
    }
}
