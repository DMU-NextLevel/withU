package NextLevel.demo.funding.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.funding.dto.request.SaveOptionRequestDto;
import NextLevel.demo.funding.dto.response.OptionResponseDto;
import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.funding.service.OptionService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OptionController {

    private final OptionService optionService;

    @GetMapping("/api1/option/{projectId}")
    public ResponseEntity<?> getAllOptions(@PathVariable("projectId") Long projectId) {
        List<OptionEntity> entities = optionService.getAllOptions(projectId);
        List<OptionResponseDto> dtos = entities.stream().map(OptionResponseDto::of).toList();
        return ResponseEntity.ok().body(new SuccessResponse("success", dtos));
    }

    @PostMapping("/api1/option/{projectId}")
    public ResponseEntity<?> addOption(@PathVariable("projectId") Long projectId, @RequestBody @Valid SaveOptionRequestDto dto) {
        dto.setUserId(JWTUtil.getUserIdFromSecurityContext());
        dto.setProjectId(projectId);
        optionService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("success", null));
    }

    @PutMapping("/api1/option/{optionId}")
    public ResponseEntity<?> updateOption(@PathVariable("optionId") Long optionId, @RequestBody @Valid SaveOptionRequestDto dto) {
        dto.setUserId(JWTUtil.getUserIdFromSecurityContext());
        dto.setOptionId(optionId);
        optionService.update(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("success", null));
    }
    
    // 옵션 삭제 미정
}
