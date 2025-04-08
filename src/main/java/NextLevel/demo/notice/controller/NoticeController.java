package NextLevel.demo.notice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class NoticeController {
    @GetMapping("/public/notice")
    public ResponseEntity<?> getAllNotice() {
        return null;
    }

    @PostMapping("/admin/notice")
    public ResponseEntity<?> addNotice() {
        return null;
    }

    @PutMapping("/admin/notice")
    public ResponseEntity<?> updateNotice() {
        return null;
    }

    @DeleteMapping("/admin/notice")
    public ResponseEntity<?> removeNotice() {
        return null;
    }
}
