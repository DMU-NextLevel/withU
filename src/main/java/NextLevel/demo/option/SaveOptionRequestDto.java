package NextLevel.demo.option;

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

    public OptionEntity toEntity(ProjectEntity project){
        return OptionEntity.builder()
            .id(optionId)
            .price(price)
            .description(description)
            .project(project)
            .build();
    }

}
