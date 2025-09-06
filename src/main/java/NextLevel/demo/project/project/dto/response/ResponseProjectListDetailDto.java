package NextLevel.demo.project.project.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import NextLevel.demo.img.ImgDto;
import NextLevel.demo.img.entity.ImgEntity;
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

    private ImgDto titleImg;

    private Double completionRate;

    private Long likeCount;

    private List<String> tags;

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
        ImgEntity titleImg,
        Date createdAt,
        Date expired,
        long goal,
        Double completionRate,
        long likeCount,
        long userCount,
        long isLiked,
        long viewCount,
        long totalCount // 전체 조건에 만족하는 project 갯수
    ) {
        this.id = id;
        this.title = title;
        this.titleImg = new ImgDto(titleImg);
        this.completionRate = completionRate!=null ?  new BigDecimal(completionRate).setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.userCount = userCount;
        this.isLiked = isLiked != 0L;
        this.isExpired = expired.before(new Date());
        this.expired = expired;
        this.viewCount = viewCount;
        this.totalCount = totalCount;
    }

}