package NextLevel.demo.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestTossPaymentDto {
    private String paymentType;
    private String orderId;
    private String paymentKey;
    private Long amount;

    @Override
    public String toString() {
        return "RequestTossPaymentDto{" +
            "paymentType='" + paymentType + '\'' +
            ", orderId='" + orderId + '\'' +
            ", paymentKey='" + paymentKey + '\'' +
            ", amount=" + amount +
            '}';
    }
}
