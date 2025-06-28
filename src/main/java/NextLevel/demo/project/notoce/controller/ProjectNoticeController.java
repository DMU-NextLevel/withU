package NextLevel.demo.project.notoce.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.project.notoce.dto.request.SaveProjectNoticeRequestDto;
import NextLevel.demo.project.notoce.service.ProjectNoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProjectNoticeController {

    private final ProjectNoticeService projectNoticeService;

    @PostMapping("/api1/project/{projectId}/notice")
    public ResponseEntity<?> addProjectNotice(@PathVariable("projectId") long projectId, @ModelAttribute @Valid SaveProjectNoticeRequestDto dto) {
        dto.setProjectId(projectId);
        projectNoticeService.saveProjectNotice(dto, null);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    @PutMapping("/api1/project/notice/{noticeId}")
    public ResponseEntity<?> updateProjectNotice(@PathVariable("noticeId") long noticeId, @ModelAttribute @Valid SaveProjectNoticeRequestDto dto) {
        dto.setNoticeId(noticeId);
        projectNoticeService.saveProjectNotice(dto, null);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    @DeleteMapping("/api1/project/notice/{noticeId}")
    public ResponseEntity<?> deleteProjectNotice(@PathVariable("noticeId") long noticeId){
        projectNoticeService.deleteProjectNotice(noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }
}
