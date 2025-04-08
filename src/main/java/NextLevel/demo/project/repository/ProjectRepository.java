package NextLevel.demo.project.repository;

import NextLevel.demo.project.entity.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query("select p from ProjectEntity p "
        + "left join fetch p.user u "
        + "left join fetch u.userDetail "
        + "left join fetch p.imgs "
        + "where p.id = :id")
    Optional<ProjectEntity> findByIdWithAll(@Param("id") Long id);

    @Query("select p from ProjectEntity p left join fetch p.tags pt left join fetch pt.tag where p.id in :ids")
    List<ProjectEntity> findTagsByIds(@Param("ids") List<Long> ids);

}
