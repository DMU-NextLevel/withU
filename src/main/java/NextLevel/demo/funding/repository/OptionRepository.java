package NextLevel.demo.funding.repository;

import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    @Query("select op from OptionEntity op left join fetch op.project p left join fetch p.user where op.id = :optionId")
    Optional<OptionEntity> findByIdWithAll(@Param("optionId") Long optionId);

    List<OptionEntity> findByProjectId(Long projectId);
}
