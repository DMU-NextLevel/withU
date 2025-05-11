package NextLevel.demo.project.project.dto.response;

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

    private Boolean isRecommend;
    private Date expired;
    private Boolean isExpired; // 만뢰 된 project인지?

    public ResponseProjectListDto(Long id, String title, Date created_at, Date expired
                , Double completionRate, Long recommendCount, Long userCount, String titleImg, Long isRecommend ,Long totalCount) {
        this.id = id;
        this.title = title;
        this.titleImg = titleImg;
        this.completionRate = completionRate == null ? 0.0 : completionRate;
        this.recommendCount = recommendCount;
        this.pageCount = (totalCount / PAGE_COUNT) +1 ;
        this.createdAt = created_at;
        this.totalCount = totalCount;
        this.userCount = userCount;
        this.isRecommend = isRecommend == 1;
        this.isExpired = expired.before(new Date());
        this.expired = expired;
    }

}