package NextLevel.demo.project.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.project.entity.TagEntity;
import NextLevel.demo.project.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

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
