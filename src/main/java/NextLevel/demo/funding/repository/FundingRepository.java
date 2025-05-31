package NextLevel.demo.funding.repository;

import NextLevel.demo.funding.entity.FundingEntity;
import NextLevel.demo.funding.entity.OptionEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingRepository extends JpaRepository<FundingEntity, Long> {

    Optional<FundingEntity> findByUser_IdAndOption_Id(Long userId, Long optionId);

    Optional<FundingEntity> findByUser_IdAndProject_IdAndOption_IdIsNull(Long userId, Long projectId);

    @Query("select f from FundingEntity f "
        + "left join fetch f.user "
        + "where f.project.id=:id and f.option is null")
    List<FundingEntity> findByProjectIdAndOptionIdIsNull(@Param("id") Long projectId);

    @Query("select count(f) from FundingEntity f where f.id =:id group by f.user")
    Long getProjectFundingUserCount(@Param("id") Long projectId);
}
