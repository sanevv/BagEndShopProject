package com.github.semiprojectshop.service.sihu.order;

import com.github.semiprojectshop.repository.sihu.order.Orders;
import com.github.semiprojectshop.repository.sihu.order.OrdersJpa;
import com.github.semiprojectshop.repository.sihu.order.OrdersProduct;
import com.github.semiprojectshop.repository.sihu.product.cart.ProductCart;
import com.github.semiprojectshop.repository.sihu.product.cart.ProductCartJpa;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.web.sihu.dto.product.order.PaymentResponse;
import com.github.semiprojectshop.web.sihu.dto.product.order.PaymentsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersJpa ordersJpa;
    private final ProductCartJpa productCartJpa;


    @Transactional
    public PaymentResponse saveTempOrder(List<PaymentsRequest> paymentsRequests, long loginUserId) {
        MyUser myUser = MyUser.onlyId(loginUserId);
        List<Long> productCartIds = paymentsRequests.stream()
                .map(PaymentsRequest::getProductCartId)
                .toList();
        List<OrdersProduct> ordersProducts = paymentsRequests.stream()
                .map(request -> OrdersProduct.fromPaymentsRequest(request))
                .toList();
        Orders orders = Orders.fromMyUserAndOrders(myUser,ordersProducts);
        Orders savedOrder = ordersJpa.save(orders);
//        long totalPrice = paymentsRequests.stream()
//                .mapToLong(PaymentsRequest::getAtPrice)
//                .sum();
        long totalDiscountRate = paymentsRequests.stream()
                .mapToLong(request->
                    (long) (request.getAtPrice() * (1 - request.getAtDiscountRate())))
                .sum();
        long totalQuantity = paymentsRequests.stream()
                .mapToLong(PaymentsRequest::getQuantity)
                .sum();
        return PaymentResponse.of(savedOrder.getOrdersId(), productCartIds, totalDiscountRate, totalQuantity);


    }
    @Transactional
    public void modifyOrderStatus(long ordersId, List<Long> productCartIds) {
        Orders orders = ordersJpa.findById(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 존재하지 않습니다."));

        if (orders.getStatus() == Orders.orderStatus.COMPLETED) {
            throw new IllegalStateException("이미 주문이 완료된 상태입니다.");
        }
        orders.modifyStatus();
        if(productCartIds != null &&!productCartIds.isEmpty() )
            productCartJpa.deleteAllById(productCartIds);
    }

    @Transactional
    public void deleteOrder(long ordersId) {
        Orders orders = ordersJpa.findById(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 존재하지 않습니다."));
        if (orders.getStatus() == Orders.orderStatus.COMPLETED) {
            throw new IllegalStateException("이미 완료된 주문은 취소할 수 없습니다.");
        }
        ordersJpa.delete(orders);
    }
}
