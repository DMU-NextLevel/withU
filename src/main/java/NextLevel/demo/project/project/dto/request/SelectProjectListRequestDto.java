package NextLevel.demo.project.project.dto.request;

import NextLevel.demo.project.project.repository.MyPageWhere;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SelectProjectListRequestDto {
    private Long userId;
    private List<Long> tag;
    private String order;
    private String search;
    private Boolean desc;
    private Integer page = 0;
    private MyPageWhere myPageWhere;
    private Long pageCount = 10000L;

//    public String getTag() {
//        if(tag == null || tag.isEmpty())
//            return null;
//
//        StringBuilder builder = new StringBuilder("");
//        tag.forEach(t->builder.append(t).append(","));
//        builder.deleteCharAt(builder.length()-1);
//        return builder.toString();
//    }

    public List<Long> getTagIds() {
        return tag;
    }

    public void setMyPageWhere(String myPageWhere) {
        this.myPageWhere = MyPageWhere.valueOf(myPageWhere);
        this.tag = new ArrayList<Long>();
        this.search = "";
        this.order = this.myPageWhere.getOrderType();
        this.desc = this.myPageWhere.isDesc();
    }
}
