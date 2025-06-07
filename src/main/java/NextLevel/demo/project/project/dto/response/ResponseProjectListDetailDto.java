package NextLevel.demo.project.project.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class ResponseProjectListDetailDto {
    private Long id;
    private String title;

    private String titleImg;

    private Double completionRate;

    private Long likeCount;

    private List<String> tags;

    private Long pageCount;
    private Long totalCount;
    private Long userCount;
    private Long viewCount;

    private Date createdAt;

    private Boolean isLiked;
    private Date expired;
    private Boolean isExpired;

    public ResponseProjectListDetailDto(
        Long id,
        String title,
        String titleImg,
        Date createdAt,
        Date expired,
        long goal,
        Double completionRate,
        long likeCount,
        long userCount,
        long isLiked,
        long viewCount,
        long totalCount
    ) {
        this.id = id;
        this.title = title;
        this.titleImg = titleImg;
        this.completionRate = completionRate!=null ?  new BigDecimal(completionRate).setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.userCount = userCount;
        this.isLiked = isLiked != 0L;
        this.isExpired = expired.before(new Date());
        this.expired = expired;
        this.viewCount = viewCount;
        this.totalCount = totalCount;
        //        this.pageCount = (totalCount / PAGE_COUNT) +1 ;
    }

}