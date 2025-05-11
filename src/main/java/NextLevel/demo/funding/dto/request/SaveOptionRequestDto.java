package NextLevel.demo.funding.dto.request;

import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveOptionRequestDto {

    private Long optionId;
    @NotNull
    private Integer price;
    @NotEmpty
    private String description;

    private Long projectId;
    private Long userId;

    private ProjectEntity project;

    public OptionEntity toEntity(){
        return OptionEntity.builder()
            .id(optionId)
            .price(price)
            .description(description)
            .project(project)
            .build();
    }

}
