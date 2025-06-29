package com.github.semiprojectshop.service.sihu.order;

import com.github.semiprojectshop.repository.sihu.order.Orders;
import com.github.semiprojectshop.repository.sihu.order.OrdersJpa;
import com.github.semiprojectshop.repository.sihu.order.OrdersProduct;
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


    @Transactional
    public PaymentResponse saveTempOrder(List<PaymentsRequest> paymentsRequests) {
        MyUser myUser = MyUser.onlyId(1L);//TODO: 로그인 기능 구현 후 제거

        List<OrdersProduct> ordersProducts = paymentsRequests.stream()
                .map(request -> OrdersProduct.fromPaymentsRequest(request))
                .toList();
        Orders orders = Orders.fromMyUserAndOrders(myUser,ordersProducts);
        Orders savedOrder = ordersJpa.save(orders);
        long totalPrice = paymentsRequests.stream()
                .mapToLong(PaymentsRequest::getAtPrice)
                .sum();
        long totalQuantity = paymentsRequests.stream()
                .mapToLong(PaymentsRequest::getQuantity)
                .sum();
        return PaymentResponse.of(savedOrder.getOrdersId(), totalPrice, totalQuantity);


    }
}
