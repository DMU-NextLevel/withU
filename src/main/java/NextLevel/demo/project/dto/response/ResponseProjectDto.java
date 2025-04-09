package NextLevel.demo.project.dto.response;

import java.util.Date;
import java.util.List;

public class ResponseProjectDto {
    private Long id;
    private String title;
    private String content;

    private String titleImg;
    private List<String> imgs;

    private Date createdAt;
    private Date expiredAt;
    private Boolean isExpired;

    private String authorNickName;
    private String authorEmail;

    private Boolean isAuthor;

    private Long goal;
    private Long sum; // 현재 모인 금액의 총액
    private Double completionRate;

    private Long recommendCount;
    private Long userCount;

}
