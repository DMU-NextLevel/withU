package NextLevel.demo.project.notoce.dto.response;

import NextLevel.demo.project.notoce.entity.ProjectNoticeEntity;
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
public class ResponseProjectNoticeDto {

    private Long Id;

    private String title;

    private String content;

    public static ResponseProjectNoticeDto of(ProjectNoticeEntity entity) {
        ResponseProjectNoticeDto dto = new ResponseProjectNoticeDto();
        dto.Id = entity.getId();
        dto.content = entity.getContent();
        dto.title = entity.getTitle();
        return dto;
    }
}
