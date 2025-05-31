package NextLevel.demo.project.project.service;

import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.entity.ProjectViewEntity;
import NextLevel.demo.project.project.repository.ProjectViewRepository;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectViewService {

    private final ProjectViewRepository projectViewRepository;
    private final EntityManager entityManager;

    public void save(ProjectEntity project, Long userId) {
        projectViewRepository.save(
            ProjectViewEntity
                .builder()
                .project(project)
                .user(entityManager.getReference(UserEntity.class, userId))
                .build()
        );
    }
}
