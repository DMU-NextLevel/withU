package NextLevel.demo.funding;

public class FundingUtil {

    public static Double getCompletionRate(Long maxSum, Long goal) {
        return ( Double.valueOf(maxSum) * 100 ) / goal;
    }
}
