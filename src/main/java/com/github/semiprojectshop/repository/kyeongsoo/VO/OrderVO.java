package com.github.semiprojectshop.repository.kyeongsoo.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class OrderVO {

    private int orderId;
    private int userId;
    private String createdAt;
    private String status;
    List<OrdersProductVO> ordersProductList;
    List<AdminMemberProVO> adminMemberProList;

}
