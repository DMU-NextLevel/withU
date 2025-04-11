package NextLevel.demo.project.story.repository;

import NextLevel.demo.project.story.entity.ProjectStoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStoryRepository extends JpaRepository<ProjectStoryEntity, Long> {

}
