package NextLevel.demo.funding.dto;

import NextLevel.demo.funding.entity.FundingEntity;
import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundingDto {

    @NotNull
    private int totalPrice;
    private int optionPrice;
    private int freePrice;

    private Long optionId;

    @NotNull
    private Long projectId;

    private Long userId;

    private UserEntity user;
    private ProjectEntity project;
    private OptionEntity option;

    public FundingEntity toEntity(){
        return FundingEntity.builder()
            .freePrice(freePrice)
            .user(user)
            .project(project)
            .option(option)
            .build();
    }

    @Builder
    public FundingDto(int totalPrice, Long optionId, Long projectId, Long userId) {
        this.totalPrice = totalPrice;
        this.optionId = optionId;
        this.projectId = projectId;
        this.userId = userId;
    }

    //    public static FundingDto of(FundingEntity fundingEntity){
//
//    }

}
