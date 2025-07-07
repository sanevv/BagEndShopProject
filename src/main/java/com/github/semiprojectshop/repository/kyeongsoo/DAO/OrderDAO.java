package com.github.semiprojectshop.repository.kyeongsoo.DAO;

import com.github.semiprojectshop.repository.kyeongsoo.VO.OrderVO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

    // 주문 상세 정보 조회(이미지, 상품명, 가격 등)
    List<OrderVO> getOrderDetails(String userid) throws SQLException;

    // 모든 회원 주문 정보 조회
    List<OrderVO> getAllOrderDetails() throws SQLException;

    // 주문 상태 업데이트
    int updateOrderStatus(int orderId, String status) throws SQLException;



}
