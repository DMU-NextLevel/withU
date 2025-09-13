package NextLevel.demo.user.repository;

import NextLevel.demo.funding.repository.FundingDslRepository;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDto;
import NextLevel.demo.project.project.entity.QProjectEntity;
import NextLevel.demo.project.select.SelectProjectListDslRepository;
import NextLevel.demo.project.view.QProjectViewEntity;
import NextLevel.demo.user.dto.user.request.RequestMyPageProjectListDto;
import NextLevel.demo.user.entity.QLikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserProjectDslRepository {

    private final SelectProjectListDslRepository selectProjectRepository;
    private final FundingDslRepository fundingDslRepository;

    public ResponseProjectListDto myProject(RequestMyPageProjectListDto dto) {
        SelectProjectListDslRepository.Builder builder = selectProjectRepository.builder();
        builder = where(builder, dto.getUserId(), dto.getType());
        return builder
                .limit(dto.getLimit(), dto.getPage())
                .commit(dto.getUserId());
    }

    private SelectProjectListDslRepository.Builder where(SelectProjectListDslRepository.Builder builder, Long userId, MyPageProjectListType type) {
        switch(type) {
            case MyPageProjectListType.PROJECT:
                builder.where(QProjectEntity.class, (project)->project.user.id.eq(userId));
                break;
            case MyPageProjectListType.FUNDING:
                builder.where(QProjectEntity.class, (project)->fundingDslRepository.isFunding(project, userId));
                break;
            case MyPageProjectListType.LIKE:
                builder.where(QLikeEntity.class, (like)->like.user.id.eq(userId));
                break;
            case MyPageProjectListType.VIEW:
                builder.where(QProjectViewEntity.class, (view)->view.user.id.eq(userId));
                builder.where(QProjectViewEntity.class, (view)->view.createAt.before(LocalDateTime.now().minusDays(10)));
                break;
        }
        return builder;
    }

}
