package NextLevel.demo.funding.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RequestFundingDto {
    private RequestFreeFundingDto free;
    private RequestOptionFundingDto option;

    public void setUserId(Long userId) {
        if(free != null)
            free.setUserId(userId);
        if(option != null)
            option.setUserId(userId);
    }
}
