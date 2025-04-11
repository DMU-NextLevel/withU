package NextLevel.demo.project.story.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.repository.ProjectRepository;
import NextLevel.demo.project.story.entity.ProjectStoryEntity;
import NextLevel.demo.project.story.repository.ProjectStoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectStoryService {

    private final ProjectRepository projectRepository;
    private final ImgService imgService;

    public void saveProjectStory(Long projectId, Long userId, List<MultipartFile> imgs){
        ProjectEntity oldProject = projectRepository.findById(projectId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_PROJECT, projectId.toString()));
        if(oldProject.getUser().getId() != userId)
            throw new CustomException(ErrorCode.NOT_AUTHOR);

        List<ProjectStoryEntity> ps = imgs
            .stream()
            .map(
                i->imgService.saveImg(i)
            )
            .map(
                i->ProjectStoryEntity.builder().build()
            )
            .toList();

        List<ImgEntity> oldImgs = oldProject.getImgs().stream().map(pe->pe.getImg()).toList();
        oldProject.setImgs(ps);

        projectRepository.save(oldProject);
        oldImgs.forEach(i->imgService.deleteImg(i));
    }
}
