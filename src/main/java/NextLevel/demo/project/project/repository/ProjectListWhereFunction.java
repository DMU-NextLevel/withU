package NextLevel.demo.project.project.repository;

import NextLevel.demo.project.project.entity.QProjectEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;

@FunctionalInterface
public interface ProjectListWhereFunction {

    BooleanExpression where(QProjectEntity projectEntity, EntityPathBase... entities);

}
