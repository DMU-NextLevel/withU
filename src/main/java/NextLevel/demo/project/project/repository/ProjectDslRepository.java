package NextLevel.demo.project.project.repository;

import NextLevel.demo.project.project.dto.request.RequestMainPageProjectListDto;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
import NextLevel.demo.project.project.entity.QProjectEntity;
import NextLevel.demo.project.tag.entity.QProjectTagEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static NextLevel.demo.project.project.entity.QProjectEntity.projectEntity;
import static NextLevel.demo.project.tag.entity.QProjectTagEntity.projectTagEntity;

@Repository
@RequiredArgsConstructor
public class ProjectDslRepository {

    private final SelectProjectListDslRepository selectProjectRepository;

    public List<ResponseProjectListDetailDto> selectProjectDsl(RequestMainPageProjectListDto dto) {
        ProjectListWhereFunction<QProjectTagEntity> whereFunction =
                (project, tag)-> where(project, tag, dto.getTagIds(), dto.getSearch());
        return selectProjectRepository.selectProjects(dto.getUserId(), whereFunction, QProjectTagEntity.class, ProjectOrderType.getType(dto.getOrder()),dto.getDesc(), dto.getLimit(), dto.getOffset());
    }

    private BooleanExpression where(QProjectEntity projectEntity, QProjectTagEntity projectTagEntity, List<Long> tagIds, String search) {
        BooleanExpression where = Expressions.TRUE;
        if(tagIds != null && !tagIds.isEmpty()) {
            where = where.and(projectTagEntity.tag.id.in(tagIds));
        }
        if(search != null && !search.isEmpty()) {
            where = where.and(projectEntity.title.like("%"+search+"%"));
        }
        return where;
    }

}
