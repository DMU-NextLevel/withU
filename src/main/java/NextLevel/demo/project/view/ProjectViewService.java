package NextLevel.demo.project.view;

import NextLevel.demo.project.project.dto.request.RequestMainPageProjectListDto;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
import NextLevel.demo.project.project.dto.response.ResponseProjectListDto;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.repository.ProjectViewRepository;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectViewService {

    private final ProjectViewRepository projectViewRepository;
    private final EntityManager entityManager;
    private final ProjectViewDslRepository projectViewDslRepository;

    public void save(ProjectEntity project, Long userId) {
        if(userId == null) {
            return;
        }
        projectViewRepository.save(
            ProjectViewEntity
                .builder()
                .project(project)
                .user(entityManager.getReference(UserEntity.class, userId))
                .build()
        );
    }

//    public ResponseProjectListDto recentProjectList(RequestMainPageProjectListDto dto) {
//        List<ResponseProjectListDetailDto> projectDetailList = projectViewDslRepository.findRecentProjectList(dto);
//        return new ResponseProjectListDto(projectDetailList, dto.getPageCount(), dto.getPage());
//    }
}
