package NextLevel.demo.project.project.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SelectProjectListRequestDto {
    private Long userId;
    private List<Long> tag;
    private String order;
    private String search;
    private Boolean desc;
    private Integer page;

    public String getTag() {
        StringBuilder builder = new StringBuilder();
        tag.forEach(t->builder.append(t).append(","));
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
