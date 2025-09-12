package NextLevel.demo.user.repository;

import NextLevel.demo.funding.repository.FundingDslRepository;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
import NextLevel.demo.project.project.entity.QProjectEntity;
import NextLevel.demo.project.project.repository.ProjectListWhereFunction;
import NextLevel.demo.project.project.repository.ProjectOrderType;
import NextLevel.demo.project.project.repository.SelectProjectListDslRepository;
import NextLevel.demo.user.dto.user.request.RequestMyPageProjectListDto;
import NextLevel.demo.user.entity.QLikeEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserProjectDslRepository {

    private final SelectProjectListDslRepository selectProjectRepository;
    private final FundingDslRepository fundingDslRepository;

    public List<ResponseProjectListDetailDto> selectProjectDsl(RequestMyPageProjectListDto dto) {
        ProjectListWhereFunction whereFunction =
                (project, like)->{
                    return where(project, like, dto.getUserId(), dto.getType());
                };
        return selectProjectRepository.selectProjects(dto.getUserId(), whereFunction, QLikeEntity.class, ProjectOrderType.CREATED , true, dto.getLimit(), dto.getOffset());
    }

    private BooleanExpression where(QProjectEntity projectEntity, QLikeEntity likeEntity, Long userId, MyPageProjectListType myPageProjectListType) {
        switch(myPageProjectListType) {
            case MyPageProjectListType.PROJECT:
                return projectEntity.user.id.eq(userId);
            case MyPageProjectListType.LIKE:
                return likeEntity.user.id.eq(userId);
            case MyPageProjectListType.FUNDING:
                return fundingDslRepository.isFunding(projectEntity, userId);
            case MyPageProjectListType.VIEW:
                return
        }
        return Expressions.TRUE;
    }

}
