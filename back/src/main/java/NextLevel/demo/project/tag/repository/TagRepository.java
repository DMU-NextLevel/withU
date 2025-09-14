package NextLevel.demo.project.tag.repository;

import NextLevel.demo.project.tag.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

}
