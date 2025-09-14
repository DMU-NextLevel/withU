package NextLevel.demo.project.project.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectValidateService {
    private final ProjectRepository projectRepository;

    public ProjectEntity getProjectEntity(Long id) {
        return projectRepository.findById(id).orElseThrow(
                ()->{return new CustomException(ErrorCode.NOT_FOUND, "project");}
        );
    }

    public ProjectEntity validateAuthor(Long projectId, Long userId) {
        ProjectEntity project = getProjectEntity(projectId);
        if(project.getUser().getId() != userId){
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }
        return project;
    }
}
