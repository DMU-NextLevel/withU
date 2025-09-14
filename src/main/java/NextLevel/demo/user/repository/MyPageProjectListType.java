package NextLevel.demo.user.repository;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum MyPageProjectListType {
    PROJECT, // 내가 생성한 project
    FUNDING, // 내가 펀딩한 project
    LIKE, // 내가 좋아요한 project
    VIEW, // 최근 조회한 project
    ;
}
