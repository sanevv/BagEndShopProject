package com.github.semiprojectshop.web.kyeongsoo;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.kyeongsoo.DAO.OrderDAO;
import com.github.semiprojectshop.repository.kyeongsoo.VO.OrderQueryVO;
import com.github.semiprojectshop.repository.kyeongsoo.VO.OrderVO;
import com.github.semiprojectshop.repository.kyeongsoo.VO.OrdersProductVO;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO;
import com.github.semiprojectshop.repository.kyeongsoo.model.DaoCustom;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderShow")
@RequiredArgsConstructor
public class OrderDetailsController {
    private final OrderDAO orderDAO;

    @GetMapping("/orderDetails")
    public String orderDetails(HttpServletRequest request) throws SQLException {

        HttpSession session = request.getSession();
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        String userid = String.valueOf(loginUser.getUserId());

        // 주문 상세 정보 조회(이미지, 상품명, 가격 등)
        List<OrderVO> orderDetailsList = orderDAO.getOrderDetails(userid);

        request.setAttribute("orderDetailsList", orderDetailsList);


        

        return "order/orderDetails";
    }


}



















