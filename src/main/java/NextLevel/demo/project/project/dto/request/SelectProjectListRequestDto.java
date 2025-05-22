package NextLevel.demo.project.project.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
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
        if(tag == null || tag.isEmpty())
            return null;

        StringBuilder builder = new StringBuilder("");
        tag.forEach(t->builder.append(t).append(","));
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
