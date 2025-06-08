package NextLevel.demo.notice.dto;

import NextLevel.demo.notice.entity.NoticeEntity;
import NextLevel.demo.notice.entity.NoticeImgEntity;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Setter @Getter
public class SaveNoticeDto {

    private Long id;

    private String title;
    private String content;

    private List<MultipartFile> imgs;

    private List<NoticeImgEntity> imgEntities;

    public NoticeEntity toEntity() {
        return NoticeEntity
            .builder()
            .id(id)
            .title(title)
            .content(content)
            .build();
    }
}
