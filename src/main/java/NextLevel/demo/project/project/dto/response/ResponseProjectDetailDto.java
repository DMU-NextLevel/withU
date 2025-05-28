package NextLevel.demo.project.project.dto.response;

import NextLevel.demo.funding.FundingUtil;
import NextLevel.demo.project.project.entity.ProjectEntity;
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

    private String titleImg;

    private Date createdAt;
    private Date expiredAt;
    private Boolean isExpired;

    private String authorNickName;

    private Boolean isAuthor;

    private Long goal;
    private Long sum; // 현재 모인 금액의 총액
    private Double completionRate;

    private int recommendCount;
    private int fundingCount;
    private Long userCount;

    public static ResponseProjectDetailDto of(ProjectEntity entity, Long userCount, Long userId) {
        ResponseProjectDetailDto dto = new ResponseProjectDetailDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setTitleImg(entity.getTitleImg().getUri());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExpiredAt(entity.getExpired());
        dto.setAuthorNickName(entity.getUser().getNickName());
        dto.setGoal(entity.getGoal());
        dto.setSum(entity.getFundings().stream().mapToLong(e->e.getFreePrice()).sum());
        dto.setCompletionRate(FundingUtil.getCompletionRate(dto.sum, entity.getGoal()));
        dto.setRecommendCount(entity.getLikes().size());
        dto.setFundingCount(entity.getFundings().size());
        dto.setIsAuthor(entity.getUser().getId() == userId);
        dto.setIsExpired( new Date().before(entity.getExpired()) );
        dto.userCount = userCount==null?0:userCount;
        return dto;
    }

}
