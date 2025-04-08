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

    private String authorNickName;
    private String authorEmail;

    private Long goal;
    private Double completionRate;

    private Long recommendCount;
    private Long userCount;

}
