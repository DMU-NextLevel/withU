package NextLevel.demo.payment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NaverPaymentService {

    @Value("${payment.naver.chain_id}")
    private String clientId;
    @Value("${payment.naver.client_secret}")
    private String clientSecret;
    @Value("${payment.naver.request_url}")
    private String requestUrl;
    @Value("${payment.naver.chain_id}")
    private String chainId;
    @Value("${payment.naver.return_url}")
    private String returnUrl;

    public void requestPayment() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        headers.set("Content-Type", "application/json");

        Map<String, Object> payParams = new HashMap<String, Object>();
        payParams.put("merchantPayKey", "paykey");
        payParams.put("merchantUserKey", "userkey");
        payParams.put("productName", "product name");
        payParams.put("productCount", 5);
        payParams.put("totalPayAmount", 100);
        payParams.put("taxScopeAmount", 0);
        payParams.put("taxExScopeAmount", 0);
        payParams.put("returnUrl", returnUrl);

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<>();
        item.put("category", "PRODUCT");
        item.put("categoryId", "GENERAL");
        item.put("uid", "iphone");
        item.put("name", "name");
        item.put("count", 1);
        items.add(item);

        payParams.put("productItems", items);

        JSONObject json = new JSONObject(payParams);

        HttpEntity<?> request = new HttpEntity<>(json.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> res = restTemplate.postForObject(requestUrl, request, Map.class);

        res.forEach((k,v)->{log.info(k+":"+v);});
    }
}
