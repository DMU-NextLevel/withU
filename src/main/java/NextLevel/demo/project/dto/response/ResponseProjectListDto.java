package NextLevel.demo.project.dto.response;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseProjectListDto {

    // @Value("${page_count}")
    private static long PAGE_COUNT = 20;

    private Long id;
    private String title;

    private String titleImg;

    private Double completionRate;

    private Long recommendCount;

    private List<String> tags;

    private Long pageCount;
    private Long totalCount;
    private Long userCount;

    private Date createdAt;

    //for db
    private Long authorId;

    public ResponseProjectListDto(Long id, String title, Long userId, Date created_at
                , Double completionRate, Long recommendCount, Long userCount, String titleImg, Long totalCount) {
        this.id = id;
        this.title = title;
        this.titleImg = "/src/"+titleImg;
        this.completionRate = completionRate == null ? 0.0 : completionRate;
        this.recommendCount = recommendCount;
        this.authorId = authorId;
        this.pageCount = totalCount / PAGE_COUNT;
        this.createdAt = created_at;
        this.totalCount = totalCount;
        this.userCount = userCount;
    }

}