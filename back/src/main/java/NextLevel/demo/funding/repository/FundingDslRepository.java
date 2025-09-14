package NextLevel.demo.funding.repository;

import static NextLevel.demo.funding.entity.QFreeFundingEntity.freeFundingEntity;
import static NextLevel.demo.funding.entity.QOptionFundingEntity.optionFundingEntity;
import static NextLevel.demo.project.project.entity.QProjectEntity.projectEntity;

import NextLevel.demo.project.project.entity.QProjectEntity;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FundingDslRepository {

    private final JPAQueryFactory queryFactory;

    public boolean isFreeFunding(Long projectId, Long userId) {
        return queryFactory
                .select(isFreeFunding(projectEntity, userId))
                .from(projectEntity)
                .where(projectEntity.id.eq(projectId))
                .fetchOne();
    }

    public Expression<Double> completeRate(QProjectEntity projectEntity) {
        QProjectEntity newProjectEntity = new QProjectEntity("projectEntity3");
        return queryFactory
                .select(
                        optionFundingEntity.option.price.longValue().multiply(optionFundingEntity.count).sum().coalesce(0L)
                                .add(freeFundingEntity.price.longValue().sum().coalesce(0L))
                                .doubleValue()
                                .divide(projectEntity.goal)
                                .multiply(100)
                )
                .from(newProjectEntity)
                .leftJoin(optionFundingEntity).on(optionFundingEntity.option.project.id.eq(projectEntity.id))
                .leftJoin(freeFundingEntity).on(freeFundingEntity.project.id.eq(projectEntity.id))
                .where(newProjectEntity.id.eq(projectEntity.id));
    }

    public Expression<Long> fundingUserCount(QProjectEntity projectEntity) {
        QProjectEntity newProjectEntity = new QProjectEntity("projectEntity2");
        return JPAExpressions
                .select(optionFundingEntity.count.sum().add(freeFundingEntity.count()))
                .from(newProjectEntity)
                .leftJoin(optionFundingEntity).on(optionFundingEntity.option.project.id.eq(projectEntity.id))
                .leftJoin(freeFundingEntity).on(freeFundingEntity.project.id.eq(projectEntity.id))
                .where(newProjectEntity.id.eq(projectEntity.id));
    }


    // select from funding where f.project = :id and f.user = :id
    public BooleanExpression isFunding(QProjectEntity project, Long userId) {
        return isFreeFunding(project, userId).or(isOptionFunding(project, userId));
    }

    public BooleanExpression isFreeFunding(QProjectEntity project, Long userId) {
        if(userId == null)
            return Expressions.FALSE;
        return queryFactory
                .select()
                .from(freeFundingEntity)
                .where(freeFundingEntity.project.id.eq(project.id).and(freeFundingEntity.user.id.eq(userId)))
                .exists();
    }

    public BooleanExpression isOptionFunding(QProjectEntity project, Long userId) {
        if(userId == null)
            return Expressions.FALSE;
        return queryFactory
                .select(optionFundingEntity)
                .from(optionFundingEntity)
                .where(optionFundingEntity.option.project.id.eq(project.id).and(optionFundingEntity.user.id.eq(userId)))
                .exists();
    }

}
