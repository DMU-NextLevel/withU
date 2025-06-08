package NextLevel.demo.notice.dto;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.notice.entity.NoticeEntity;
import NextLevel.demo.notice.entity.NoticeImgEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class ResponseNoticeDto {

    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdAt;

    private List<String> imgs;

    public static ResponseNoticeDto of(NoticeEntity entity) {
        ResponseNoticeDto dto = new ResponseNoticeDto();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.content = entity.getContent();
        dto.createdAt = entity.getCreatedAt();
        dto.imgs = entity.getImgs().stream().map(NoticeImgEntity::getImg).map(ImgEntity::getUri).toList();
        return dto;
    }

}
