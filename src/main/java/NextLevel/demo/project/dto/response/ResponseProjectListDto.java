package NextLevel.demo.project.dto.response;

import NextLevel.demo.funding.entity.FundingEntity;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.project.entity.ProjectEntity;
import NextLevel.demo.project.entity.TagEntity;
import NextLevel.demo.recommend.entity.RecommendEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseProjectListDto {
    private long page;
    private long total;
    private List<Detail> details;

    public static ResponseProjectListDto of(List<ProjectEntity> entities, int page) {
        ResponseProjectListDto dto = new ResponseProjectListDto();
        dto.setTotal(entities.size());
        dto.setPage(page);
        dto.setDetails(entities.stream().map(e->Detail.of(e)).toList());
        return dto;
    }
}

@Getter
@Setter
@NoArgsConstructor
@ToString
class Detail {

    @Value("${page_count}")
    private long PAGE_COUNT;

    private long id;
    private String title;

    private String titleImg;

    private double completion_rate;

    private long recommend_count;

    private List<String> tags;

    // for db
//    private Long tagId;
//    private long authorId;

    static Detail of(ProjectEntity project) {
        Detail dto = new Detail();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setTitleImg("/img/" + project.getTitleImg().getUri());
        dto.setCompletion_rate(getCompletion_rate(project));
        dto.setRecommend_count(project.getRecommends().stream().count());
        dto.setTags(project.getTags().stream().map(t -> t.getTag().getName()).toList());
        return dto;
    }

    private static double getCompletion_rate(ProjectEntity project) {
        int sum = project.getFundings().stream().mapToInt(FundingEntity::getPrice).sum();
        return sum * 100 / project.getGoal();
    }
}


//    public ResponseProjectListDto(long id, String title, long tagId, long authorId
//                , double completion_rate , long recommend_count, long userCount, String titleImg, long totalCount) {
//        this.id = id;
//        this.title = title;
//        this.titleImg = "/src/"+titleImg;
//        this.completion_rate = completion_rate;
//        this.recommend_count = recommend_count;
//        this.tagId = tagId;
//        this.authorId = authorId;
//        this.pageCount = totalCount / PAGE_COUNT;
//    }
