package NextLevel.demo.funding.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.funding.dto.request.SaveOptionRequestDto;
import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.funding.repository.OptionRepository;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProjectRepository projectRepository;

    public void add(SaveOptionRequestDto dto){
        ProjectEntity project = projectRepository.findByIdWithAll(dto.getProjectId()).orElseThrow(
            ()->{throw new CustomException(ErrorCode.NOT_FOUND_PROJECT, dto.getProjectId().toString());}
        );

        if(project.getUser().getId() != dto.getUserId()){
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        optionRepository.save(dto.toEntity());
    }

    public void update(SaveOptionRequestDto dto){
        OptionEntity option = optionRepository.findByIdWithAll(dto.getOptionId()).orElseThrow(
            ()->{throw new CustomException(ErrorCode.NOT_FOUND_OPTION, dto.getOptionId().toString());}
        );

        if(option.getProject().getUser().getId() != dto.getUserId()){
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        optionRepository.save(dto.toEntity());
    }

    public List<OptionEntity> getAllOptions(Long projectId) {
        return optionRepository.findByProjectId(projectId);
    }

}
