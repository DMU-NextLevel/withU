import { laodTossPayments } from "@tosspayments/payment-sdk";

const handlePayment = (subject) => {
  const randomId = btoa(1234);

  if(true) {
    laodTossPayments(test_ck_kYG57Eba3GR0DPKMQ7X98pWDOxmA).then(tossPayments => {
      tossPayments.requestPayment(subject, {
        amount: 1,
        orderId: "orderId",
        orderName: "orderName",
        customerName: "customerName",
        successUrl: "https://localhost:8080/payment/toss/success",
        failUrl: "https://localhost:8080/payment/toss/fail"
      })
    });
  }
}