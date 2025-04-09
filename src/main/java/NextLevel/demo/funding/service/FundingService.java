package NextLevel.demo.funding.service;

import org.springframework.stereotype.Service;

@Service
public class FundingService {


    public static Double getCompletionRate(Long maxSum, Long goal) {
        return ( Double.valueOf(maxSum) * 100 ) / goal;
    }
}
