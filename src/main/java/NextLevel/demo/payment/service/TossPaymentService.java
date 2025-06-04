package NextLevel.demo.payment.service;

import NextLevel.demo.payment.dto.RequestTossPaymentDto;
import NextLevel.demo.user.repository.UserRepository;
import NextLevel.demo.user.service.UserService;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class TossPaymentService {

    @Value("${payment.toss.secret_key}")
    private String secretKey;
    @Value("${payment.toss.approve_url}")
    private String approveUrl;

    private final UserRepository userRepository;

    @Transactional
    public void approvePayment(RequestTossPaymentDto dto, Long userId) {
        // toss 로 결제 요청 보내기
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic "+Base64.getEncoder().encodeToString((secretKey+":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity http = new HttpEntity<Object>(dto, headers); // dto 에서 paymentType 제외 시켜야함

        Map<String, Object> output = new RestTemplate().postForObject(approveUrl, http, Map.class);

        // output.forEach((key, value) -> {log.info("Key: " + key + " value: " + value);});

        // new RestTemplate().postForObject(approveUrl, http, RequestTossPaymentDto.class);

        log.info("Approved toss payment userId: " + userId + " price : " + output.get("totalAmount"));

        // 결제 내영 만큼 user에게 point를 추가함
        userRepository.addPointByUserId((Integer) output.get("totalAmount"), userId);
    }
}
