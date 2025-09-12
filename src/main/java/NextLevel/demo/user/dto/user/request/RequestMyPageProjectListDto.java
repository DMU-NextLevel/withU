package NextLevel.demo.user.dto.user.request;

import NextLevel.demo.user.repository.MyPageProjectListType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RequestMyPageProjectListDto {

    private Long userId;
    private Integer page = 0;
    private Long pageCount = 10L; // default 10
    private MyPageProjectListType type;

    public long getLimit() {
        return pageCount;
    }
    public long getOffset() {
        return pageCount * page;
    }

}
