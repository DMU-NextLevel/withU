package NextLevel.demo.funding.repository;

import NextLevel.demo.funding.entity.FreeFundingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FreeFundingRepository extends JpaRepository<FreeFundingEntity, Long> {

    @Query("select sum(f.price) from FreeFundingEntity f where f.project.id = :projectId")
    Long getTotalPriceByProject(@Param("projectId") Long projectId);

}
