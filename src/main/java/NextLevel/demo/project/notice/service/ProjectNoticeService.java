package NextLevel.demo.project.notice.service;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgServiceImpl;
import NextLevel.demo.img.service.ImgTransaction;
import NextLevel.demo.project.notice.dto.request.SaveProjectNoticeRequestDto;
import NextLevel.demo.project.notice.entity.ProjectNoticeEntity;
import NextLevel.demo.project.notice.repository.ProjectNoticeRepository;
import NextLevel.demo.project.project.entity.ProjectEntity;
import jakarta.persistence.EntityManager;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectNoticeService {

    private final ProjectNoticeRepository projectNoticeRepository;
    private final ImgServiceImpl imgService;
    private final EntityManager entityManager;

    @Transactional
    @ImgTransaction
    public void saveProjectNotice(SaveProjectNoticeRequestDto dto, ArrayList<Path> imgPaths) {
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
            saveImg = imgService.saveImg(dto.getImg(), imgPaths);
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
