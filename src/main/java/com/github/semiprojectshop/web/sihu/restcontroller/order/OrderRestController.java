package com.github.semiprojectshop.web.sihu.restcontroller.order;

import com.github.semiprojectshop.service.sihu.order.OrderService;
import com.github.semiprojectshop.service.sihu.product.ProductService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.OrderProductRequest;
import com.github.semiprojectshop.web.sihu.dto.product.order.PaymentResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.PaymentsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final ProductService productService;
    private final OrderService orderService;
    @PostMapping("/confirm")//조회용 PostMapping
    public CustomResponse<List<CartListResponse>> requestOrderList(@RequestBody List<OrderProductRequest> orderProductRequests) {
        //TODO: 로그인 안했으면 예외
        List<CartListResponse> responses = productService.getProductInfoForOrder(orderProductRequests);
        return CustomResponse.ofOk("주문 상품 정보 조회 성공", responses);
    }

    @PostMapping("/payment")
    public CustomResponse<PaymentResponse> requestSaveOrder(@RequestBody List<PaymentsRequest> paymentsRequests){
        PaymentResponse paymentResponse = orderService.saveTempOrder(paymentsRequests);
        return CustomResponse.ofOk("주문 정보 저장 성공", paymentResponse);
    }
}
