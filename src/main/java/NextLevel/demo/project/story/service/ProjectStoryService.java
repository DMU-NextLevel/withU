package NextLevel.demo.project.story.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.repository.ProjectRepository;
import NextLevel.demo.project.story.entity.ProjectStoryEntity;
import NextLevel.demo.project.story.repository.ProjectStoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectStoryService {

    private final ProjectRepository projectRepository;
    private final ProjectStoryRepository projectStoryRepository;
    private final ImgService imgService;

    @Transactional
    public void saveProjectStory(Long projectId, Long userId, List<MultipartFile> imgFiles){
        ProjectEntity oldProject = projectRepository.findByIdWithAll(projectId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND, "project"));
        if(oldProject.getUser().getId() != userId)
            throw new CustomException(ErrorCode.NOT_AUTHOR);

        Set<ProjectStoryEntity> oldStories = oldProject.getStories();
        List<ImgEntity> oldImgs = oldProject.getStories().stream().map(pe->pe.getImg()).toList();

        List<ImgEntity> newImgs = new ArrayList<>();
        imgFiles
            .stream()
            .forEach(i->{newImgs.add(imgService.saveImg(i));});

        Set<ProjectStoryEntity> newStories = newImgs
            .stream()
            .map(
                i->
                ProjectStoryEntity.builder()
                    .project(oldProject)
                    .img(i)
                    .build()
            )
            .collect(Collectors.toSet());

        projectStoryRepository.saveAll(newStories);
        projectStoryRepository.deleteAll(oldStories.stream().map(ProjectStoryEntity::getId).toList());
        oldImgs.forEach(i->imgService.deleteImg(i));
    }
}
