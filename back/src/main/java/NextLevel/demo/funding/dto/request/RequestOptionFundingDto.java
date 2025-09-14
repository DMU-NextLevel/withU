package NextLevel.demo.funding.dto.request;

import NextLevel.demo.option.OptionEntity;
import NextLevel.demo.funding.entity.OptionFundingEntity;
import NextLevel.demo.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestOptionFundingDto {

    private Long optionId;
    private Long count;

    private Long userId;

    public OptionFundingEntity toEntity(UserEntity user, OptionEntity option) {
        return OptionFundingEntity
                .builder()
                .option(option)
                .user(user)
                .count(count)
                .build();
    }

}
