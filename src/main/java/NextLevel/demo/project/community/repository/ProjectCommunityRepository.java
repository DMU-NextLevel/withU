package NextLevel.demo.project.community.repository;

import NextLevel.demo.project.community.entity.ProjectCommunityEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectCommunityRepository extends JpaRepository<ProjectCommunityEntity, Long> {
    @Query("select c from ProjectCommunityEntity c "
        + "left join fetch c.project p "
        + "left join fetch c.asker akser "
        + "left join fetch p.user "
        + "where c.id = :id")
    Optional<ProjectCommunityEntity> findByIdWithAll(@Param("id") long id);
}
