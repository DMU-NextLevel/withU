package NextLevel.demo.controller.payment;

import NextLevel.demo.dto.PaymentDto.RequestTossPaymentDto;
import NextLevel.demo.service.payment.NaverPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final NaverPaymentService naverPaymentService;

    @GetMapping("/payment/toss/success")
    public ResponseEntity<?> tossPaymentSuccess(RequestTossPaymentDto dto) {
        log.info("tossPaymentSuccess");
        return null;
    }

    @GetMapping("/payment/toss/fail")
    public ResponseEntity<?> tossPaymentFail() {
        log.info("tossPaymentFail");
        return null;
    }
}
