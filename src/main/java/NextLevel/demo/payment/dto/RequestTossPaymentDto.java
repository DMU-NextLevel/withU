package NextLevel.demo.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestTossPaymentDto {
    private String orderId;
    private String paymentKey;
    private Long amount;
}
