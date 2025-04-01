package NextLevel.demo.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@RequiredArgsConstructor
@Log4j2
public class ProjectController {

    @PostMapping
    public ResponseEntity<?> createNewProject() {
        return null;
    }

    @PutMapping
    public ResponseEntity<?> updateProject() {
        return null;
    }
}
