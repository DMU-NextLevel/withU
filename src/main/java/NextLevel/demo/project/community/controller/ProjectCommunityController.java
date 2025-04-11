package NextLevel.demo.project.community.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.project.community.dto.ResponseProjectCommunityDto;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.service.ProjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProjectCommunityController {

    private final ProjectService projectService;

    @GetMapping("/public/project/{projectId}/community")
    public ResponseEntity<?> getProjectCommunity(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getProjectCommunityAndNoticeById(projectId);

        List<ResponseProjectCommunityDto> dtos = project.getCommunities().stream().map(pc->ResponseProjectCommunityDto.of(pc)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", dtos));
    }
}
