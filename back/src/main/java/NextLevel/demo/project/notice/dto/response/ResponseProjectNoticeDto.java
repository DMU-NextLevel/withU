package NextLevel.demo.project.notice.dto.response;

import NextLevel.demo.img.ImgDto;
import NextLevel.demo.project.notice.entity.ProjectNoticeEntity;

import java.time.LocalDateTime;
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

    private ImgDto img;

    private LocalDateTime createTime;

    public static ResponseProjectNoticeDto of(ProjectNoticeEntity entity) {
        ResponseProjectNoticeDto dto = new ResponseProjectNoticeDto();
        dto.Id = entity.getId();
        dto.content = entity.getContent();
        dto.title = entity.getTitle();
        dto.img = new ImgDto(entity.getImg());
        dto.createTime = entity.getCreatedAt();
        return dto;
    }
}
