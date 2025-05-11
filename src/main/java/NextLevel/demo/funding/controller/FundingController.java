package NextLevel.demo.funding.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.funding.dto.FundingDto;
import NextLevel.demo.funding.service.FundingService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/funding")
@Controller
@RequiredArgsConstructor
@Slf4j
public class FundingController {

    private final FundingService fundingService;

    @PostMapping
    public ResponseEntity<?> funding(@RequestBody @Valid FundingDto dto) {
        dto.setUserId(JWTUtil.getUserIdFromSecurityContext());
        fundingService.funding(dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }
}
