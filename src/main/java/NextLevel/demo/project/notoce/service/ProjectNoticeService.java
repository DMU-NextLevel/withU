package NextLevel.demo.project.notoce.service;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.project.notoce.dto.request.SaveProjectNoticeRequestDto;
import NextLevel.demo.project.notoce.entity.ProjectNoticeEntity;
import NextLevel.demo.project.notoce.repository.ProjectNoticeRepository;
import NextLevel.demo.project.project.entity.ProjectEntity;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectNoticeService {

    private final ProjectNoticeRepository projectNoticeRepository;
    private final ImgService imgService;
    private final EntityManager entityManager;

    public void saveProjectNotice(SaveProjectNoticeRequestDto dto) {
        Optional<ProjectNoticeEntity> oldNotice = projectNoticeRepository.findByIdWithProject(dto.getNoticeId());

        ImgEntity saveImg = null;
        ProjectEntity saveProject = null;

        if(oldNotice.isPresent()) {
            // update
            saveProject = oldNotice.get().getProject();
            if(dto.getImg() == null || dto.getImg().isEmpty())
                saveImg = oldNotice.get().getImg();
        }else {
            // save
            saveImg = imgService.saveImg(dto.getImg());
            saveProject = entityManager.getReference(ProjectEntity.class, dto.getProjectId());
        }

        dto.setProjectEntity(saveProject);
        dto.setImgEntity(saveImg);
        projectNoticeRepository.save(dto.toEntity());
    }

    public void deleteProjectNotice(Long id) {
        projectNoticeRepository.deleteById(id);
    }

}
