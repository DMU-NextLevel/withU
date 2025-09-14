package NextLevel.demo.project.select;

import com.querydsl.core.types.dsl.EntityPathBase;

@FunctionalInterface
public interface ProjectSelectFunctionInterface<RT, T extends EntityPathBase> {

    RT function(T entity);

}
