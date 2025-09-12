package NextLevel.demo.project.project.repository;

import com.querydsl.core.types.dsl.EntityPathBase;

public class ProjectSelectWhere {

    public EntityPathBase[] entityClasses;
    public ProjectListWhereFunction whereFunction;

    public ProjectSelectWhere(ProjectListWhereFunction whereFunction, EntityPathBase... classes){
        this.whereFunction = whereFunction;
        this.entityClasses = classes;
    }
}
