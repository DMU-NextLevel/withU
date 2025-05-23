package NextLevel.demo.project.community.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.project.community.dto.request.SaveCommunityDto;
import NextLevel.demo.project.community.entity.ProjectCommunityEntity;
import NextLevel.demo.project.community.repository.ProjectCommunityRepository;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.repository.ProjectRepository;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectCommunityService {

    private final ProjectCommunityRepository projectCommunityRepository;
    private final ProjectRepository projectRepository;
    private final EntityManager entityManager;

    @Transactional
    public void save(SaveCommunityDto dto) {
        if (dto.getId() == null) {
            saveAsker(dto, projectRepository.findByIdWithAll(dto.getProjectId()).orElseThrow(() -> {
                throw new CustomException(ErrorCode.NOT_FOUND, "project");
            }));
            return;
        }

        ProjectCommunityEntity community = projectCommunityRepository.findByIdWithAll(dto.getId())
            .orElseThrow(
                () -> {
                    throw new CustomException(ErrorCode.NOT_FOUND, "project community");
                }
            );

        if (community.getProject().getUser().getId() == dto.getUserId()) {
            saveAnserer(dto, community);
            return;
        }
        if (community.getAsker().getId() == dto.getUserId()){
            saveAsker(dto, community.getProject());
            return;
        }

        throw new CustomException(ErrorCode.NOT_AUTHOR);
    }

    private void saveAsker(SaveCommunityDto dto, ProjectEntity project) {
        dto.setAskAt(new Date());
        dto.setAsker(entityManager.getReference(UserEntity.class, dto.getUserId()));
        dto.setProject(project);

        projectCommunityRepository.save(dto.toEntity());
    }

    private void saveAnserer(SaveCommunityDto dto, ProjectCommunityEntity community){
        dto.setAnswerAt(new Date());
        dto.setProject(community.getProject());
        dto.setAsker(community.getAsker());
        dto.setAskAt(community.getAskAt());

        projectCommunityRepository.save(dto.toEntity());
    }

    public void delete(Long id){
        projectCommunityRepository.deleteById(id);
    }
}
