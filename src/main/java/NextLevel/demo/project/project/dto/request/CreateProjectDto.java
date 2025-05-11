package NextLevel.demo.project.project.dto.request;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.story.entity.ProjectStoryEntity;
import NextLevel.demo.project.project.entity.ProjectTagEntity;
import NextLevel.demo.user.entity.UserEntity;
import java.util.List;
import java.util.Set;
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

    private String expired;
    private Long goal;

    private List<Long> tags;
    // @NotNull
    private MultipartFile titleImg;
    // @NotNull
    private List<MultipartFile> imgs;

    private List<ProjectTagEntity> tagEntitys;
    private ImgEntity titleImgEntity;
    private Set<ProjectStoryEntity> imgEntitys;

    public ProjectEntity toEntity() {
        try {
            return ProjectEntity.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .tags(tagEntitys)
                .titleImg(titleImgEntity)
                .stories(imgEntitys)
                .expired(expired)
                .goal(goal)
                .build();
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.ERROR_EXPIRED_DATE_CONVERSION, expired);
        }
    }
}
