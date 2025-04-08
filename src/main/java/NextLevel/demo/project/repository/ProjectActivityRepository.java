package NextLevel.demo.project.repository;

import NextLevel.demo.project.ProjectOrderType;
import NextLevel.demo.project.dto.response.ResponseProjectListDto;
import NextLevel.demo.project.entity.ProjectEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProjectActivityRepository {

    private final EntityManager entityManager;

    @Value("${page_count}")
    private int pageCount;

    // tag 값이 있느면 where 추가
    // order by type에 맞게 order
    @Transactional
    public List<ProjectEntity> getAll(Long tagId, ProjectOrderType orderType, int page) {
        String select = """ 
            select p from ProjectEntity p 
            left join fetch p.titleImg 
            left join fetch p.tags pt 
            left join fetch TagEntity t on t = pt.tag 
            left join fetch FundingEntity f on f.user.id = p.user.id
            left join fetch RecommendEntity r on r.user.id = p.user.id
            """;
        String where = "";
        String order = "";
        String groupBy = "";

        if(tagId != null) {
            where = " where t.id = " + tagId + " ";
        }

        if(orderType == null)
            orderType = ProjectOrderType.RECOMMEND;

        groupBy = " group by p.id ";
        // order = " order by " + orderType.type +" ";

        Query query = entityManager.createQuery(select + where + groupBy + order, ProjectEntity.class);

        List<ProjectEntity> dtos = query.getResultList();
        dtos.forEach(dto -> {log.info(dto.toString());});
        return dtos;
    }
}
