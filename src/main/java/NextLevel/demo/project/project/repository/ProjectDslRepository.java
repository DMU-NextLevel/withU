//package NextLevel.demo.project.project.repository;
//
//import static NextLevel.demo.funding.entity.QFundingEntity.fundingEntity;
//import static NextLevel.demo.project.project.entity.QProjectEntity.projectEntity;
//import static NextLevel.demo.project.project.entity.QProjectTagEntity.projectTagEntity;
//import static NextLevel.demo.user.entity.QLikeEntity.likeEntity;
//import static NextLevel.demo.img.entity.QImgEntity.imgEntity;
//
//import NextLevel.demo.project.project.dto.request.SelectProjectListRequestDto;
//import NextLevel.demo.project.project.dto.response.ResponseProjectListDto;
//import NextLevel.demo.project.project.entity.QProjectEntity;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.Tuple;
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.JPQLQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class ProjectDslRepository {
//
//    private final JPAQueryFactory queryFactory;
//
//    public List<ResponseProjectListDto> selectProjectDsl(SelectProjectListRequestDto dto) {
//
//        return queryFactory
//            .select(Projections.constructor(ResponseProjectListDto.class,
//                projectEntity.id,
//                projectEntity.title,
//                projectEntity.createdAt,
//                projectEntity.expired,
//                // completion_rate
//                JPAExpressions.select(
//                        Expressions.numberTemplate(Double.class,
//                            "cast({0} as double) * 100.0 / {1}",
//                            fSub.freePrice.add(opSub.price).sum(), p.goal
//                        )
//                    ).from(fSub)
//                    .leftJoin(opSub).on(opSub.id.eq(fSub.optionId))
//                    .where(fSub.project.id.eq(p.id)),
//
//
//                selectLikeCount(projectEntity),
//                selectFundingUserCount(projectEntity),
//
//                // title_img
//                JPAExpressions.select(imgEntity.uri)
//                    .from(imgEntity)
//                    .where(imgEntity.id.eq(projectEntity.titleImg.id)),
//
//                // is_like (기본값 0)
//                selectIsLike(projectEntity, dto.getUserId())
//
//            ))
//            .selectProject(dto.getTag(), dto.getSearch())
//            // .groupBy(projectEntity.id)
//            .orderBy(
//                JPAExpressions.select(rSub.userId.countDistinct())
//                    .from(rSub)
//                    .where(rSub.projectId.eq(p.id))
//                    .desc()
//            )
//            .limit(8)
//            .offset(0)
//            .fetch();
//    }
//
//    private JPQLQuery<Tuple> selectProject(List<Long> tagIds, String search) {
//        return JPAExpressions
//            .select()
//            .from(projectEntity)
//            .leftJoin(projectTagEntity).on(projectEntity.id.eq(projectTagEntity.project.id))
//            .where(projectTagEntity.id.in(tagIds).and(projectEntity.title.like("%"+search+"%")));
//    }
//
//    private JPQLQuery<Long> selectLikeCount(QProjectEntity p) {
//        return JPAExpressions
//            .select(likeEntity.user.id.countDistinct())
//            .from(likeEntity)
//            .where(likeEntity.project.id.eq(p.id));
//    }
//
//    private JPQLQuery<Long> selectFundingUserCount(QProjectEntity p) {
//        return JPAExpressions
//            .select(fundingEntity.user.id.count())
//            .from(fundingEntity)
//            .where(fundingEntity.project.id.eq(p.id));
//    }
//
//    private JPQLQuery<Long> selectIsLike(QProjectEntity p, Long userId) {
//        if(userId == null)
//            return JPAExpressions.select(Expressions.constant(0L));
//
//        return JPAExpressions
//            .select(likeEntity.count())
//            .from(likeEntity)
//            .where(likeEntity.project.id.eq(p.id).and(likeEntity.user.id.eq(userId)));
//    }
//
//    private OrderSpecifier<?> orderBy(QProjectEntity projectEntity, ProjectOrderType orderType){
//        switch (orderType){
//            case USER:
//                return JPAExpressions
//                    .select(fundingEntity.user.id.count())
//                    .from(fundingEntity)
//                    .where(fundingEntity.project.id.eq(projectEntity.id))
//                    .asNumber();
//        }
//    }
//}
