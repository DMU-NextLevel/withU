package NextLevel.demo.funding.dto.request;

import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestFundingDto {

    @NotNull
    private int price;
    @NotNull
    private int count;

    private Long optionId;

    @NotNull
    private Long projectId;

    private Long userId;

    private UserEntity user;
    private ProjectEntity project;
    private OptionEntity option;

    //    public static FundingDto of(FundingEntity fundingEntity){
//
//    }

}
