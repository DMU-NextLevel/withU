package NextLevel.demo.project.project.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseProjectListDto {
    private List<ResponseProjectListDetailDto> projects;
    private long totalCount; // projects 에 존재함
    private long pageCount; // page 당 반환 project 갯수
    private long page; // 요청시 들어옴

    public ResponseProjectListDto(List<ResponseProjectListDetailDto> projects, long totalCount, long pageCount, long page) {
        this.projects = projects;
        this.pageCount = pageCount;
        this.page = page;
        this.totalCount = totalCount;
    }
}
