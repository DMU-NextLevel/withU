package NextLevel.demo.project.project.repository;

import NextLevel.demo.project.project.dto.request.SelectProjectListRequestDto;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDto;
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
    public List<ResponseProjectListDto> getAll(SelectProjectListRequestDto dto) {
        String select = """ 
            WITH filtered_projects AS (
                SELECT distinct p.*
                FROM project p 
                join project_tag pt on p.id = pt.project_id
                where %s 
            ),
            project_count AS (
                SELECT COUNT(*) AS total_count
                FROM filtered_projects
            )
            SELECT fp.id, fp.title, fp.created_at, fp.expired ,
                (CAST((SELECT SUM(f.free_price + op.price) FROM funding f left join `option` op on op.id = f.option_id WHERE f.project_id = fp.id) AS DOUBLE) * 100.0 / fp.goal) AS completion_rate,
                (SELECT COUNT(DISTINCT r.user_id) FROM `like` r WHERE r.project_id = fp.id) AS like_count, 
                (SELECT COUNT(f.user_id) FROM funding f WHERE f.project_id = fp.id) AS user_count, 
                (SELECT i.uri FROM img i WHERE i.id = fp.img_id) AS title_img, 
                (cast(%sas signed) )as is_like, 
                pc.total_count
            FROM filtered_projects fp
            CROSS JOIN project_count pc
            %s 
            %s
            """;
        String where = " true ";
        String isRecommend = "";
        String order = "";
        String limit = "";

        if(dto.getTag() != null) {
            where = " pt.tag_id in ("+ dto.getTag() + ") ";
        }

        if(dto.getSearch() != null && !dto.getSearch().isEmpty()) {
            where += " and p.title like '%"+dto.getSearch()+"%' ";
        }

        if(dto.getUserId() != null)
            isRecommend = "if (exists (select r.id from `like` r where r.project_id = fp.id and r.user_id = "+dto.getUserId()+"), 1, 0)";
        else
            isRecommend = "0 ";

        order = " order by "+ProjectOrderType.getType(dto.getOrder()).type+" ";
        if(dto.getDesc() != null && dto.getDesc()) {
            order += " desc ";
        }

        Integer page = dto.getPage();
        if(page == null)
            page = 1;

        limit = " limit " + pageCount + " offset " + (page-1) * pageCount + " ";

        Query query = entityManager.createNativeQuery(String.format(select, where, isRecommend ,order, limit), ResponseProjectListDto.class);

        List<ResponseProjectListDto> dtos = query.getResultList();
        return dtos;
    }
}
