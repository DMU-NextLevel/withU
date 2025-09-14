package NextLevel.demo.payment.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.payment.dto.RequestTossPaymentDto;
import NextLevel.demo.payment.service.TossPaymentService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.websocket.server.PathParam;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final TossPaymentService tossPaymentService;

    @GetMapping("/payment/toss/approve")
    public ResponseEntity<?> tossPaymentSuccess(@ModelAttribute RequestTossPaymentDto dto) {
        tossPaymentService.approvePayment(dto, JWTUtil.getUserIdFromSecurityContext());

        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }
}
