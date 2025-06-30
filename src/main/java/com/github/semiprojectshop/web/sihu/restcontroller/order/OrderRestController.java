package com.github.semiprojectshop.web.sihu.restcontroller.order;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyViewException;
import com.github.semiprojectshop.service.sihu.order.OrderService;
import com.github.semiprojectshop.service.sihu.product.ProductService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.OrderProductRequest;
import com.github.semiprojectshop.web.sihu.dto.product.order.PaymentResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.PaymentsRequest;
import jakarta.servlet.http.HttpSession;
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
    public CustomResponse<List<CartListResponse>> requestOrderList(@RequestBody List<OrderProductRequest> orderProductRequests,
                                                                   HttpSession session) {
        if (session.getAttribute("loginUser") == null)
            throw CustomMyViewException.fromMessage("로그인 후 주문을 이용해주세요.");

        List<CartListResponse> responses = productService.getProductInfoForOrder(orderProductRequests);
        return CustomResponse.ofOk("주문 상품 정보 조회 성공", responses);
    }

    @PostMapping("/payment")
    public CustomResponse<PaymentResponse> requestSaveOrder(@RequestBody List<PaymentsRequest> paymentsRequests,
                                                            HttpSession session
    ){
        if(session.getAttribute("loginUser") == null)
            throw CustomMyException.fromMessage("로그인 후 주문을 진행해주세요.");
        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");

        PaymentResponse paymentResponse = orderService.saveTempOrder(paymentsRequests, memberVO.getUserId());
        return CustomResponse.ofOk("주문 정보 저장 성공", paymentResponse);
    }
    @PostMapping("/success")
    public CustomResponse<String> requestOrderSuccess(@RequestParam long ordersId,
                                                      @RequestParam(required = false) List<Long> productCartIds){
        orderService.modifyOrderStatus(ordersId, productCartIds);
        return CustomResponse.emptyDataOk("주문 완료 성공");
    }
    @DeleteMapping("/fail")
    public CustomResponse<String> requestOrderFail(@RequestParam long ordersId){
        orderService.deleteOrder(ordersId);
        return CustomResponse.emptyDataOk("주문 취소 성공");
    }
}
