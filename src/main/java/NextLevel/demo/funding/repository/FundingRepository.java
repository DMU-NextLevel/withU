package NextLevel.demo.funding.repository;

import NextLevel.demo.funding.entity.FundingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingRepository extends JpaRepository<FundingEntity, Long> {

}
