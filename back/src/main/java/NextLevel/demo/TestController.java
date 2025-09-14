package NextLevel.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/payment/test")
    public String paymentTest() {
        return "toss";
    }
    @GetMapping("/public/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("public test success");
    }
    @GetMapping("/social/test")
    public ResponseEntity<String> socialTest() {
        return ResponseEntity.ok("socialTest");
    }
    @GetMapping("/api1/test")
    public ResponseEntity<String> api1Test() {
        return ResponseEntity.ok("api1Test");
    }
}
