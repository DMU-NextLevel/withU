package NextLevel.demo.project.project.repository;

import static NextLevel.demo.funding.entity.QFundingEntity.fundingEntity;
import static NextLevel.demo.project.project.entity.QProjectEntity.projectEntity;
import static NextLevel.demo.project.project.entity.QProjectViewEntity.projectViewEntity;
import static NextLevel.demo.project.tag.entity.QProjectTagEntity.projectTagEntity;
import static NextLevel.demo.user.entity.QLikeEntity.likeEntity;

import NextLevel.demo.project.project.dto.request.SelectProjectListRequestDto;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
import NextLevel.demo.project.project.entity.QProjectEntity;
import NextLevel.demo.project.tag.entity.QProjectTagEntity;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectDslRepository {
    @Value("${page_count}")
    private int pageCount;
    private final JPAQueryFactory queryFactory;

    public List<ResponseProjectListDetailDto> selectProjectDsl(SelectProjectListRequestDto dto) {

        return queryFactory
            .selectDistinct(Projections.constructor(ResponseProjectListDetailDto.class,
                projectEntity.id,
                projectEntity.title,
                projectEntity.titleImg.uri,
                projectEntity.createdAt,
                projectEntity.expired,
                projectEntity.goal,

                // completeRate
                completeRate(projectEntity),

                // like count
                likeCount(projectEntity),

                // user_count
                fundingUserCount(projectEntity),

                // is_like
                isLike(projectEntity, dto.getUserId()),

                // view_count
                viewCount(projectEntity),

                // total _ select count :: totalCount
                totalCount(projectEntity, dto)
            ))
            .from(projectEntity)
            .leftJoin(projectTagEntity).on(projectEntity.id.eq(projectTagEntity.project.id))
            .leftJoin(likeEntity).on(projectEntity.id.eq(likeEntity.project.id)).fetchJoin()
            .leftJoin(fundingEntity).on(fundingEntity.id.eq(fundingEntity.project.id))
            .where(
                where(projectEntity, projectTagEntity, dto.getUserId(), dto.getTagIds(), dto.getSearch(), dto.getMyPageWhere())
            )
            .orderBy(
                orderByType(projectEntity, dto.getUserId(), ProjectOrderType.getType(dto.getOrder()), dto.getDesc()),
                projectEntity.createdAt.desc()
            )
            .limit(dto.getPageCount())
            .offset(dto.getPageCount() * dto.getPage())
            .fetch();
    }

    private BooleanExpression where(QProjectEntity projectEntity, QProjectTagEntity projectTagEntity, Long userId, List<Long> tagIds, String search, MyPageWhere myPageWhere) {
        BooleanExpression where = Expressions.TRUE;
        if(tagIds != null && !tagIds.isEmpty()) {
            where = where.and(projectTagEntity.tag.id.in(tagIds));
        }
        if(search != null && !search.isEmpty()) {
            where = where.and(projectEntity.title.like("%"+search+"%"));
        }
        if(myPageWhere != null && userId != null) {
            where = where.and(myPage(projectEntity, userId, myPageWhere));
        }
        return where;
    }

    private BooleanExpression myPage(QProjectEntity projectEntity, Long userId, MyPageWhere myPageWhere) {
        switch(myPageWhere) {
            case MyPageWhere.PROJECT:
                return projectEntity.user.id.eq(userId);
            case MyPageWhere.LIKE:
                return likeEntity.user.id.eq(userId);
            case MyPageWhere.FUNDING:
                return fundingEntity.user.id.eq(userId);
        }
        return Expressions.TRUE;
    }

    private OrderSpecifier<?> orderByType(QProjectEntity projectEntity, Long userId, ProjectOrderType type, boolean desc) {
        ComparableExpressionBase<?> order = null;
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
                order = Expressions.asNumber(fundingUserCount(projectEntity));
                break;
            case ProjectOrderType.VIEW:
                order = Expressions.asNumber(viewCount(projectEntity));
                break;
            case ProjectOrderType.COMPLETION:
                order = Expressions.asNumber(completeRate(projectEntity));
                break;
        }
        if(desc)
            return order.desc();
        else
            return order.asc();
    }

    private Expression<?> totalCount(QProjectEntity projectEntity, SelectProjectListRequestDto dto) {
        return queryFactory
            .select(projectEntity.countDistinct())
            .from(projectEntity)
            .leftJoin(projectTagEntity).on(projectEntity.id.eq(projectTagEntity.project.id))
            .leftJoin(likeEntity).on(projectEntity.id.eq(likeEntity.project.id))
            .leftJoin(fundingEntity).on(fundingEntity.id.eq(fundingEntity.project.id))
            .where(where(projectEntity, projectTagEntity, dto.getUserId(), dto.getTagIds(), dto.getSearch(), dto.getMyPageWhere()));
    }

    private Expression<Double> completeRate(QProjectEntity projectEntity) {
        return JPAExpressions
            .select(
                fundingEntity.freePrice
                    .add(fundingEntity.option.price)
                    .sum()
                    .doubleValue()
                    .divide(projectEntity.goal)
                    .multiply(100)
            )
            .from(fundingEntity)
            .where(fundingEntity.project.id.eq(projectEntity.id));
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

    private Expression<Long> fundingUserCount(QProjectEntity projectEntity) {
        return JPAExpressions
            .select(fundingEntity.count())
            .from(fundingEntity)
            .where(fundingEntity.project.id.eq(projectEntity.id));
    }

    private Expression<Long> likeCount(QProjectEntity projectEntity) {
        return JPAExpressions
            .select(likeEntity.count())
            .from(likeEntity)
            .where(likeEntity.project.id.eq(projectEntity.id));
    }

}
