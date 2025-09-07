package NextLevel.demo.project.story.dto;

import NextLevel.demo.img.ImgDto;
import NextLevel.demo.project.story.entity.ProjectStoryEntity;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProjectStoryListDto {
    private List<ImgDto> imgs;

    public ResponseProjectStoryListDto (Set<ProjectStoryEntity> entities) {
        this.imgs = entities.stream().map(e-> new ImgDto(e.getImg())).toList();
    }
}
