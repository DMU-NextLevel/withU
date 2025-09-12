package NextLevel.demo.project.project.repository;

import NextLevel.demo.funding.repository.FundingDslRepository;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
import NextLevel.demo.project.project.entity.QProjectEntity;
import NextLevel.demo.project.view.QProjectViewEntity;
import NextLevel.demo.project.tag.entity.QProjectTagEntity;
import NextLevel.demo.user.entity.QLikeEntity;
import NextLevel.demo.user.repository.MyPageProjectListType;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SelectProjectListDslRepository {
    @Value("${page_count}")
    private int pageCount;
    private final JPAQueryFactory queryFactory;
    private final FundingDslRepository fundingDslRepository;

    private QProjectEntity projectEntity = QProjectEntity.projectEntity;
    private QProjectTagEntity projectTagEntity = QProjectTagEntity.projectTagEntity;
    private QLikeEntity likeEntity = QLikeEntity.likeEntity;
    private QProjectViewEntity projectViewEntity = QProjectViewEntity.projectViewEntity;

    public List<ResponseProjectListDetailDto> selectProjects(
            Long userId,
            ProjectSelectWhere where,
            ProjectOrderType type,
            boolean desc, long limit, long offset
    ) {
        T whereParam = null;
        if(qClass.equals(QProjectTagEntity.class))
            whereParam = (T)projectTagEntity;
        else if(qClass.equals(QLikeEntity.class))
            whereParam = (T)likeEntity;

        return queryFactory
                .selectDistinct(Projections.constructor(ResponseProjectListDetailDto.class,
                        projectEntity.id,
                        projectEntity.title,
                        projectEntity.titleImg,
                        projectEntity.createdAt,
                        projectEntity.expired,
                        projectEntity.goal,

                        // completeRate
                        fundingDslRepository.completeRate(projectEntity),

                        // like count
                        likeCount(projectEntity),

                        // user_count
                        fundingDslRepository.fundingUserCount(projectEntity),

                        // is_like
                        isLike(projectEntity, userId),

                        // view_count
                        viewCount(projectEntity),

                        // total _ select count :: totalCount
                        totalCount(projectEntity, where.whereFunction.where(projectEntity, whereParam))
                ))
                .from(projectEntity)
                .leftJoin(projectTagEntity).on(projectEntity.id.eq(projectTagEntity.project.id))
                .leftJoin(likeEntity).on(projectEntity.id.eq(likeEntity.project.id)).fetchJoin()
                .where(
                        whereFunction.where(projectEntity, whereParam)
                )
                .orderBy(
                        orderByType(projectEntity, type, desc),
                        projectEntity.createdAt.desc()
                )
                .limit(limit) //(dto.getPageCount())
                .offset(offset) // (dto.getPageCount() * dto.getPage())
                .fetch();
    }

    private OrderSpecifier<?> orderByType(QProjectEntity projectEntity, ProjectOrderType type, Boolean desc) {
        ComparableExpressionBase<?> order = projectEntity.createdAt;
        switch(type) {
            case ProjectOrderType.CREATED:
                order = projectEntity.createdAt;
                break;
            case ProjectOrderType.RECOMMEND:
                order = Expressions.asNumber(likeCount(projectEntity));
                break;
            case ProjectOrderType.EXPIRED:
                order = projectEntity.expired;
                break;
            case ProjectOrderType.USER:
                order = Expressions.asNumber(fundingDslRepository.fundingUserCount(projectEntity));
                break;
            case ProjectOrderType.VIEW:
                order = Expressions.asNumber(viewCount(projectEntity));
                break;
            case ProjectOrderType.COMPLETION:
                order = Expressions.asNumber(fundingDslRepository.completeRate(projectEntity));
                break;
        }
        if(desc == null || desc)
            return order.desc();
        else
            return order.asc();
    }

    private Expression<?> totalCount(QProjectEntity projectEntity, BooleanExpression where) {
        return queryFactory
            .select(projectEntity.countDistinct())
            .from(projectEntity)
            .leftJoin(projectTagEntity).on(projectEntity.id.eq(projectTagEntity.project.id))
            .leftJoin(likeEntity).on(projectEntity.id.eq(likeEntity.project.id))
            .where(where);
    }

    private Expression<Long> isLike(QProjectEntity projectEntity, Long userId) {
        if(userId == null)
            return Expressions.constant(0L);

        return JPAExpressions
            .select(likeEntity.user.id)
            .from(likeEntity)
            .where(likeEntity.project.id.eq(projectEntity.id).and(likeEntity.user.id.eq(userId)));
    }

    private Expression<Long> viewCount(QProjectEntity projectEntity) {
        return JPAExpressions
            .select(projectViewEntity.count())
            .from(projectViewEntity)
            .where(projectViewEntity.project.id.eq(projectEntity.id));
    }


    private Expression<Long> likeCount(QProjectEntity projectEntity) {
        return JPAExpressions
            .select(likeEntity.count())
            .from(likeEntity)
            .where(likeEntity.project.id.eq(projectEntity.id));
    }

}
