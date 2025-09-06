package NextLevel.demo.project.tag.repository;

import NextLevel.demo.project.tag.entity.ProjectTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectTagEntity, Long> {

    @Modifying
    @Query("delete from ProjectTagEntity pt where pt.project.id = :id")
    void deleteAllByProjectId(@Param("id") Long projectId);
}
