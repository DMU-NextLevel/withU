package NextLevel.demo.option;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.project.project.entity.ProjectEntity;

import java.util.List;

import NextLevel.demo.project.project.service.ProjectValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProjectValidateService projectValidateService;

    public void add(SaveOptionRequestDto dto){
        ProjectEntity project = projectValidateService.validateAuthor(dto.getProjectId(), dto.getUserId());

        optionRepository.save(dto.toEntity(project));
    }

    @Transactional
    public void update(SaveOptionRequestDto dto){
        projectValidateService.validateAuthor(dto.getProjectId(), dto.getUserId());
        OptionEntity option = optionRepository.findById(dto.getOptionId()).orElseThrow(
                ()->{return new CustomException(ErrorCode.NOT_FOUND, "option");}
        );
        option.update(dto);
    }

    public List<ResponseOptionDto> getAllOptions(Long projectId) {
        List<OptionEntity> options = optionRepository.findByProjectId(projectId);
        return options.stream().map(ResponseOptionDto::of).toList();
    }

}
