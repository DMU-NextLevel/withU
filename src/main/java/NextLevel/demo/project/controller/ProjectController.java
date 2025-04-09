package NextLevel.demo.project.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.project.ProjectOrderType;
import NextLevel.demo.project.dto.request.CreateProjectDto;
import NextLevel.demo.project.dto.response.ResponseProjectCommunityDto;
import NextLevel.demo.project.dto.response.ResponseProjectDetailDto;
import NextLevel.demo.project.dto.response.ResponseProjectListDto;
import NextLevel.demo.project.dto.response.ResponseProjectNoticeDto;
import NextLevel.demo.project.entity.ProjectEntity;
import NextLevel.demo.project.service.ProjectService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ProjectController {

    private final ProjectService projectService;

    // 추가
    @PostMapping("/api1/project")
    public ResponseEntity<?> createNewProject(@ModelAttribute CreateProjectDto dto) {
        Long userId = JWTUtil.getUserIdFromSecurityContext();
        dto.setUserId(userId);

        projectService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("success", null));
    }

    // 수정
    @PutMapping("/api1/project/{projectId}")
    public ResponseEntity<?> updateProject(@ModelAttribute @Valid CreateProjectDto dto, @PathVariable("projectId") Long projectId) {
        dto.setUserId(JWTUtil.getUserIdFromSecurityContext());
        dto.setId(projectId);

        projectService.update(dto);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 삭제

    // 모두 조회
    @GetMapping("/public/project/all")
    public ResponseEntity<?> getAllProjects(
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "tag", required = false) Long tagId,
            @RequestParam(value = "page", required = false) Integer page)
    {
        Long userId = JWTUtil.getUserIdFromSecurityContext();
        List<ResponseProjectListDto> dto = projectService.getAllProjects(tagId, userId, ProjectOrderType.getType(order), page);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success" ,dto));
    }

    // 상세 조회
    @GetMapping("/public/project/{projectId}")
    public ResponseEntity<?> getProjectDetailById(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getProjectDetailById(projectId);

        ResponseProjectDetailDto dto = ResponseProjectDetailDto.of(project);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success" ,dto));
    }

    @GetMapping("/public/project/{projectId}/story")
    public ResponseEntity<?> getProjectStory(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getProjectById(projectId);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success",
            project.getImgs().stream().map(pe->pe.getImg().getUri()).toList())
        );
    }

    @GetMapping("/public/project/{projectId}/notice")
    public ResponseEntity<?> getProjectNotice(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getProjectCommunityAndNoticeById(projectId);

        List<ResponseProjectNoticeDto> dtos = project.getNotices().stream().map(pn-> new ResponseProjectNoticeDto(pn.getId(), pn.getContent())).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", dtos));
    }

    @GetMapping("/public/project/{projectId}/community")
    public ResponseEntity<?> getProjectCommunity(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getProjectCommunityAndNoticeById(projectId);

        List<ResponseProjectCommunityDto> dtos = project.getCommunities().stream().map(pc->ResponseProjectCommunityDto.of(pc)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", dtos));
    }

}
