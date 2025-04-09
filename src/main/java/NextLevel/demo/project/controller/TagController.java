package NextLevel.demo.project.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.project.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/public/tag/all")
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success",tagService.getAllTags()));
    }
}
