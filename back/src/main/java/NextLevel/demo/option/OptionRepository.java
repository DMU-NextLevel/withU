package NextLevel.demo.option;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    @Query("select op from OptionEntity op left join fetch op.project p left join fetch p.user where op.id = :optionId")
    Optional<OptionEntity> findByIdWithAll(@Param("optionId") Long optionId);

    @Query("select op from OptionEntity op where op.project.id = :id")
    List<OptionEntity> findByProjectId(@Param("id") Long projectId);

    @Query("select op from OptionEntity op "
        + "left join fetch op.fundings f "
        + "left join fetch f.user "
        + "where op.project.id = :id")
    List<OptionEntity> findByProjectIdWithAll(@Param("id") Long projectId);
}
