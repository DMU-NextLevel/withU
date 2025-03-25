package NextLevel.demo.service.payment;

import NextLevel.demo.dto.PaymentDto.RequestTossPaymentDto;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TossPaymentService {

    @Value("${payment.toss.secret_key}")
    private String secretKey;
    @Value("${payment.toss.approve_url}")
    private String approveUrl;

    public void approvePayment(RequestTossPaymentDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(secretKey.getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity http = new HttpEntity<Object>(dto, headers); // dto 에서 paymentType 제외 시켜야함

        new RestTemplate().postForObject(approveUrl, http, RequestTossPaymentDto.class);
    }
}
