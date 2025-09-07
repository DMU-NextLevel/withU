package NextLevel.demo.project.notice.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
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

        ImgEntity savedImg = imgService.saveImg(dto.getImg(), imgPaths);
        ProjectEntity oldProject = entityManager.getReference(ProjectEntity.class, dto.getProjectId());

        projectNoticeRepository.save(dto.toEntity(savedImg, oldProject));
    }

    @Transactional
    @ImgTransaction
    public void updateNotice(SaveProjectNoticeRequestDto dto,  ArrayList<Path> imgPaths) {
        ProjectNoticeEntity notice = projectNoticeRepository.findByIdWithProject(dto.getNoticeId()).orElseThrow(
                ()->{return new CustomException(ErrorCode.NOT_FOUND, "notice");}
        );

        notice.update(dto);

        if(dto.getImg() != null || !dto.getImg().isEmpty())
            imgService.updateImg(dto.getImg(), notice.getImg(), imgPaths);
    }

    public void deleteProjectNotice(Long id) {
        projectNoticeRepository.deleteById(id);
    }

}
