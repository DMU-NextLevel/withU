package NextLevel.demo.project.notoce.repository;

import NextLevel.demo.project.notoce.entity.ProjectNoticeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectNoticeRepository extends JpaRepository<ProjectNoticeEntity, Long> {

    @Query("select n from ProjectNoticeEntity n left join fetch n.project where n.id = :noticeId")
    Optional<ProjectNoticeEntity> findByIdWithProject(@Param("noticeId") Long noticeId);
}
