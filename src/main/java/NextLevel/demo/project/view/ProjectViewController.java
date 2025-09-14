package NextLevel.demo.project.view;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.project.project.dto.request.RequestMainPageProjectListDto;
import NextLevel.demo.util.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api1/recent-project")
public class ProjectViewController {

//    private final ProjectViewDslRepository viewDslRepository;
//
//    @GetMapping
//    public ResponseEntity getRecentProjectList(@RequestBody RequestMainPageProjectListDto dto) {
//        dto.setUserId(JWTUtil.getUserIdFromSecurityContext());
//        return ResponseEntity.ok().body(new SuccessResponse("success", viewDslRepository.findRecentProjectList(dto)));
//    }

}
