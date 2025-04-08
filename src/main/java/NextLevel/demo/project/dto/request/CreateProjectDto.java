package NextLevel.demo.project.dto.request;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.project.entity.ProjectEntity;
import NextLevel.demo.project.entity.ProjectImgEntity;
import NextLevel.demo.project.entity.ProjectTagEntity;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CreateProjectDto {
    private Long id;
    private Long userId;
    private UserEntity user;

    // @NotEmpty
    private String title;
    // @NotEmpty
    private String content;

    private List<Long> tags;
    // @NotNull
    private MultipartFile titleImg;
    // @NotNull
    private List<MultipartFile> imgs;

    private List<ProjectTagEntity> tagEntitys;
    private ImgEntity titleImgEntity;
    private List<ProjectImgEntity> imgEntitys;

    public ProjectEntity toEntity() {
        return ProjectEntity.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .tags(tagEntitys)
                .titleImg(titleImgEntity)
                .imgs(imgEntitys)
                .build();
    }
}
