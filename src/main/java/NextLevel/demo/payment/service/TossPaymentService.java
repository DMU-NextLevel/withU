package NextLevel.demo.payment.service;

import NextLevel.demo.payment.dto.RequestTossPaymentDto;
import NextLevel.demo.payment.dto.ResponseTossApproveDto;
import java.util.Base64;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TossPaymentService {

    @Value("${payment.toss.secret_key}")
    private String secretKey;
    @Value("${payment.toss.approve_url}")
    private String approveUrl;

    public void approvePayment(RequestTossPaymentDto dto) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic "+Base64.getEncoder().encodeToString((secretKey+":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity http = new HttpEntity<Object>(dto, headers); // dto 에서 paymentType 제외 시켜야함

        Map<String, Object> output = new RestTemplate().postForObject(approveUrl, http, Map.class);

        log.info("approved");
        output.forEach((key, value) -> {log.info("Key: " + key + " value: " + value);});

        new RestTemplate().postForObject(approveUrl, http, RequestTossPaymentDto.class);
    }
}
