package NextLevel.demo.project.notoce.dto;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.project.notoce.entity.ProjectNoticeEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SaveProjectNoticeRequestDto {
    private Long noticeId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private MultipartFile img;

    private ImgEntity imgEntity;

    public ProjectNoticeEntity toEntity() {
        return ProjectNoticeEntity.builder()
            .id(noticeId)
            .title(title)
            .content(content)
            .img(imgEntity)
            .build();
    }
}
