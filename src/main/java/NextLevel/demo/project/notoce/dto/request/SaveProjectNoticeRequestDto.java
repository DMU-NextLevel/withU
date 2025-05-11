package NextLevel.demo.project.notoce.dto.request;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.project.notoce.entity.ProjectNoticeEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SaveProjectNoticeRequestDto {
    private Long noticeId;
    private Long projectId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private MultipartFile img;

    private ProjectEntity projectEntity;
    private ImgEntity imgEntity;

    public ProjectNoticeEntity toEntity() {
        return ProjectNoticeEntity.builder()
            .id(noticeId)
            .title(title)
            .content(content)
            .img(imgEntity)
            .project(projectEntity)
            .build();
    }
}
