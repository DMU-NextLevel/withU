package NextLevel.demo.funding.dto.request;

import NextLevel.demo.funding.FundingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestCancelFundingDto {
    private Long id;
    private FundingType fundingType;

    private Long userId;
}
