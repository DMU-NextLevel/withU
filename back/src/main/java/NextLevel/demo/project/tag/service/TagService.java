package NextLevel.demo.project.tag.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.story.entity.ProjectStoryEntity;
import NextLevel.demo.project.tag.entity.ProjectTagEntity;
import NextLevel.demo.project.tag.entity.TagEntity;
import NextLevel.demo.project.tag.repository.ProjectTagRepository;
import NextLevel.demo.project.tag.repository.TagRepository;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;

    @Transactional
    public void saveNewTags(ProjectEntity project, List<Long> tagIds){
        List<TagEntity> tags = getTagEntitysByIds(tagIds);
         tags.forEach(tag->{
             projectTagRepository.save(
                    ProjectTagEntity
                            .builder()
                            .project(project)
                            .tag(tag)
                            .build()
            );
        });
    }

    @Transactional
    public void updateTags(ProjectEntity project, List<Long> tagIds) {
        projectTagRepository.deleteAllByProjectId(project.getId());
        saveNewTags(project, tagIds);
    }

    public List<TagEntity> getTagEntitysByIds(List<Long> tagIds) {
        List<TagEntity> tagEntitys = tagRepository.findAllById(tagIds);

        if(tagEntitys.size() != tagIds.size()) {
            throw new CustomException(ErrorCode.NOT_CORRECT_TAG_SIZE);
        }

        return tagEntitys;
    }

    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }
}
