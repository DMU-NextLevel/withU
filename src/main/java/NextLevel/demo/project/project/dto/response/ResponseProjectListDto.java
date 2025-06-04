package NextLevel.demo.project.project.dto.response;

import NextLevel.demo.funding.FundingUtil;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.entity.ProjectTagEntity;
import NextLevel.demo.project.tag.entity.TagEntity;
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

    private Long likeCount;

    private List<String> tags;

    private Long pageCount;
    private Long totalCount;
    private Long userCount;
    private Long viewCount;

    private Date createdAt;

    private Boolean isLiked;
    private Date expired;
    private Boolean isExpired; // 만뢰 된 project인지?

    public ResponseProjectListDto(Long id, String title, Date created_at, Date expired
                , Double completionRate, Long likeCount, Long userCount, String titleImg, Long isLiked,
                Long viewCount, Long totalCount) {
        this.id = id;
        this.title = title;
        this.titleImg = titleImg;
        this.completionRate = completionRate == null ? 0.0 : completionRate;
        this.likeCount = likeCount;
        this.pageCount = (totalCount / PAGE_COUNT) +1 ;
        this.createdAt = created_at;
        this.totalCount = totalCount;
        this.userCount = userCount;
        this.isLiked = isLiked != 0;
        this.isExpired = expired.before(new Date());
        this.expired = expired;
        this.viewCount = viewCount;
    }

    public ResponseProjectListDto(
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
        this.completionRate = completionRate!=null ? completionRate : 0;
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

//    public ResponseProjectListDto of(ProjectEntity entity,  isLiked) {
//        ResponseProjectListDto dto = new ResponseProjectListDto();
//        dto.setId(entity.getId());
//        dto.setTitle(entity.getTitle());
//        dto.setTitleImg(entity.getTitleImg().getUri());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setCompletionRate(FundingUtil.getCompletionRate(entity.getFundings().stream().mapToLong(e->e.getFreePrice()).sum(), entity.getGoal()));
//        dto.setLikeCount((long) entity.getLikes().size());
//        dto.setTags(entity.getTags().stream().map(ProjectTagEntity::getTag).map(TagEntity::getName).toList());
//        // page count , total count
//        dto.setUserCount((long)entity.getFundings().size());
//        dto.setViewCount((long)entity.getViews().size());
//        dto.setIsLiked(entity.getLikes());
//        dto.setExpired(entity.getExpired());
//        dto.setIsExpired( new Date().before(entity.getExpired()) );
//        return dto;
//    }
}