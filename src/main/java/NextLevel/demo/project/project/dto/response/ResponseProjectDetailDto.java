package NextLevel.demo.project.project.dto.response;

import NextLevel.demo.funding.FundingUtil;
import NextLevel.demo.img.ImgDto;
import NextLevel.demo.project.project.entity.ProjectEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseProjectDetailDto {
    @Value("${page_count}")
    private Long PAGE_COUNT;

    private Long id;
    private String title;
    private String content;

    private ImgDto titleImg;

    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Boolean isExpired;

    private String authorNickName;

    private Boolean isAuthor;

    private Long goal;
    private Long sum; // 현재 모인 금액의 총액
    private Double completionRate;

    private int likeCount;
    private int fundingCount;
    private Long userCount;

    public static ResponseProjectDetailDto of(ProjectEntity entity, Long fundingPrice, Long userId) {
        ResponseProjectDetailDto dto = new ResponseProjectDetailDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setTitleImg(new ImgDto(entity.getTitleImg()));
        dto.setExpiredAt(entity.getExpired());
        dto.setAuthorNickName(entity.getUser().getNickName());
        dto.setGoal(entity.getGoal());
        dto.setSum(fundingPrice);
        dto.setCompletionRate(FundingUtil.getCompletionRate(dto.sum, dto.goal));
        dto.setLikeCount(entity.getLikes().size());
        dto.setFundingCount(entity.getFreeFundings().size());
        dto.setIsAuthor(entity.getUser().getId() == userId);
        dto.setIsExpired( LocalDateTime.now().isBefore(entity.getExpired()) );
        dto.userCount = (long) entity.getFreeFundings().size();
        return dto;
    }

}
