package NextLevel.demo.project.story.repository;

import NextLevel.demo.project.story.entity.ProjectStoryEntity;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectStoryRepository extends JpaRepository<ProjectStoryEntity, Long> {
    @Modifying
    @Query("delete from ProjectStoryEntity s where s.project.id = :id")
    void deleteAllByProjectId(@Param("id") Long projectId);

}
