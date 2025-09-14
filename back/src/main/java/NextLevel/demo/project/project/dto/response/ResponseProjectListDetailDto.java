package NextLevel.demo.project.project.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import NextLevel.demo.img.ImgDto;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.project.tag.entity.ProjectTagEntity;
import NextLevel.demo.project.tag.entity.TagEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private LocalDateTime createdAt;

    private Boolean isLiked;
    private LocalDateTime expired;
    private Boolean isExpired;

    @JsonIgnore
    private LocalDateTime projectViewCreateAt;

    public ResponseProjectListDetailDto(
        Long id,
        String title,
        ImgEntity titleImg,
        LocalDateTime createdAt,
        LocalDateTime expired,
        long goal,
        Double completionRate,
        long likeCount,
        long userCount,
        long isLiked,
        long viewCount,
        LocalDateTime projectViewCreateAt // select distinct 용 column
    ) {
        this.id = id;
        this.title = title;
        this.titleImg = new ImgDto(titleImg);
        this.completionRate = completionRate!=null ?  new BigDecimal(completionRate).setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.userCount = userCount;
        this.isLiked = isLiked != 0L;
        this.isExpired = expired.isBefore(LocalDateTime.now());
        this.expired = expired;
        this.viewCount = viewCount;
        // this.totalCount = totalCount;
        this.projectViewCreateAt = projectViewCreateAt;
    }

}