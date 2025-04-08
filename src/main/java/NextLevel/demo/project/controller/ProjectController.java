package NextLevel.demo.project.controller;

import NextLevel.demo.project.ProjectOrderType;
import NextLevel.demo.project.dto.request.CreateProjectDto;
import NextLevel.demo.project.dto.response.ResponseProjectListDto;
import NextLevel.demo.project.entity.ProjectEntity;
import NextLevel.demo.project.service.ProjectService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
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

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
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
    public ResponseEntity<?> getAllProjects(@RequestParam("order") String order, @RequestParam("tag") Long tagId, @RequestParam("page") Integer page) {
        List<ResponseProjectListDto> dto = projectService.getAllProjects(tagId, ProjectOrderType.getType(order), page);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // 상세 조회
    @GetMapping("/public/project/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable("projectId") Long projectId) {

    }
}
