package NextLevel.demo.project.view;

import NextLevel.demo.project.project.dto.request.RequestMainPageProjectListDto;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
import NextLevel.demo.project.project.repository.SelectProjectListDslRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static NextLevel.demo.project.project.entity.QProjectEntity.projectEntity;

@Repository
@RequiredArgsConstructor
public class ProjectViewDslRepository {

//    private final SelectProjectListDslRepository selectProjectListDslRepository;
//
//    public List<ResponseProjectListDetailDto> findRecentProjectList(RequestMainPageProjectListDto dto) {
//        BooleanExpression where = projectEntity.user.id.eq(dto.getUserId());
//        OrderSpecifier orderSpecifier = projectEntity.createdAt.desc();
//        return selectProjectListDslRepository.selectProjects(dto.getUserId(), where, orderSpecifier, dto.getLimit(), dto.getOffset());
//    }

}
