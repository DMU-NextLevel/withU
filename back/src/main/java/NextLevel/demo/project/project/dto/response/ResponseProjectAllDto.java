package NextLevel.demo.project.project.dto.response;

import NextLevel.demo.project.community.dto.response.ResponseCommunityListDto;
import NextLevel.demo.project.notice.dto.response.ResponseNoticeListDto;
import NextLevel.demo.project.story.dto.ResponseProjectStoryListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ResponseProjectAllDto {

    private ResponseProjectStoryListDto story;
    private ResponseNoticeListDto notice;
    private ResponseCommunityListDto community;

}
