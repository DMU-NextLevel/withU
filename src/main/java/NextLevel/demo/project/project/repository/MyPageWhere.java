package NextLevel.demo.project.project.repository;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum MyPageWhere {
    PROJECT("CREATED", true), // 내가 생성한 project
    FUNDING("COMPLETION", true), // 내가 펀딩한 project
    LIKE("LIKE", true) // 내가 좋아요한 project
    ;
    private String orderType;
    private boolean desc;
    MyPageWhere(String orderType, boolean desc) {
        this.orderType = orderType;
        this.desc = desc;
    }
}
