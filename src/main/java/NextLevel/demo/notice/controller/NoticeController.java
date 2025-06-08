package NextLevel.demo.notice.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.notice.dto.ResponseNoticeDto;
import NextLevel.demo.notice.dto.SaveNoticeDto;
import NextLevel.demo.notice.entity.NoticeEntity;
import NextLevel.demo.notice.service.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/public/notice")
    public ResponseEntity<?> getAllNotice() {
        List<NoticeEntity> notices = noticeService.getAllNotice();
        List<ResponseNoticeDto> dtos = notices.stream().map(ResponseNoticeDto::of).toList();
        return ResponseEntity.ok(new SuccessResponse("success", dtos));
    }

    @PostMapping("/admin/notice")
    public ResponseEntity<?> addNotice(@ModelAttribute SaveNoticeDto dto) {
        noticeService.addNotice(dto);

        return ResponseEntity.ok(new SuccessResponse("success", null));
    }

    @PutMapping("/admin/notice/{id}")
    public ResponseEntity<?> updateNotice(@RequestBody SaveNoticeDto dto, @PathVariable("id") Long id) {
        dto.setId(id);
        noticeService.updateNotice(dto);

        return ResponseEntity.ok(new SuccessResponse("success", null));
    }

    @PutMapping("/admin/notice/{id}/img")
    public ResponseEntity<?> updateNoticeImg(@RequestParam("imgs") List<MultipartFile> imgFiles, @PathVariable("id") Long id) {
        noticeService.updateImg(id, imgFiles);

        return ResponseEntity.ok(new SuccessResponse("success", null));
    }

    @DeleteMapping("/admin/notice/{id}")
    public ResponseEntity<?> removeNotice(@PathVariable("id") Long id) {
        noticeService.removeNotice(id);
        return ResponseEntity.ok(new SuccessResponse("success", null));
    }
}
