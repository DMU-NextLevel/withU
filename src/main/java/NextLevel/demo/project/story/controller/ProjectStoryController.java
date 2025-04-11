package NextLevel.demo.project.story.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.service.ProjectService;
import NextLevel.demo.project.story.service.ProjectStoryService;
import NextLevel.demo.util.jwt.JWTUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProjectStoryController {

    private final ProjectService projectService;
    private ProjectStoryService projectStoryService;

    @GetMapping("/public/project/{projectId}/story")
    public ResponseEntity<?> getProjectStory(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getProjectById(projectId);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success",
            project.getImgs().stream().map(pe->pe.getImg().getUri()).toList())
        );
    }
    @PostMapping("/public/project/{projectId}/story")
    public ResponseEntity<?> updateProjectStory(@PathVariable("projectId") long projectId, @RequestAttribute List<MultipartFile> imgs){
        projectStoryService.saveProjectStory(projectId, JWTUtil.getUserIdFromSecurityContext() ,imgs);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success",null));
    }

}
