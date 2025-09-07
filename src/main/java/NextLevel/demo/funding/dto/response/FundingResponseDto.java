package NextLevel.demo.funding.dto.response;

import NextLevel.demo.funding.entity.FundingEntity;
import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.user.dto.user.response.UserFundingInfoDto;
import NextLevel.demo.user.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FundingResponseDto {

    private OptionResponseDto option;
    private List<UserFundingInfoDto> funding;

    public FundingResponseDto(OptionEntity option) {
//        if(option == null)
//            this.option = new OptionResponseDto();
//        else
            this.option = OptionResponseDto.of(option);
        this.funding = option.getFundings().stream().map(FundingEntity::getUser).map(UserFundingInfoDto::of).toList();
    }

    public FundingResponseDto(List<UserEntity> user) {
        this.option = new OptionResponseDto();
        this.funding = user.stream().map(UserFundingInfoDto::of).collect(Collectors.toList());
    }
}
