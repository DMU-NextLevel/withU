package NextLevel.demo.project.notoce.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.project.notoce.dto.ResponseProjectNoticeDto;
import NextLevel.demo.project.notoce.dto.SaveProjectNoticeRequestDto;
import NextLevel.demo.project.notoce.service.ProjectNoticeService;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProjectNoticeController {

    private final ProjectService projectService;
    private final ProjectNoticeService projectNoticeService;

    @GetMapping("/public/project/{projectId}/notice")
    public ResponseEntity<?> getProjectNotice(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getProjectCommunityAndNoticeById(projectId);

        List<ResponseProjectNoticeDto> dtos = project.getNotices().stream().map(pn-> new ResponseProjectNoticeDto(pn.getId(), pn.getContent())).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", dtos));
    }
    @PostMapping("/public/project/notice/{noticeId}")
    public ResponseEntity<?> updateProjectNotice(@PathVariable("noticeId") long noticeId, @ModelAttribute @Valid SaveProjectNoticeRequestDto dto) {
        dto.setNoticeId(noticeId);
        projectNoticeService.saveProjectNotice(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }
}
