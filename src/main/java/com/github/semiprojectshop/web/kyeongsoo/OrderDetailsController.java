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
import com.github.semiprojectshop.web.kyeongsoo.dto.UpdateOrderStatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
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

        String referer = request.getHeader("Referer");

        if (referer == null) {
            // 페이지를 홈화면 으로 보낸다.
            return "redirect:/";
        }

        HttpSession session = request.getSession();
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login"; // 로그인 안된 경우
        }

        String roleId = String.valueOf(loginUser.getRoleId());
        request.setAttribute("roleId", roleId);

        if ("1".equals(roleId)) { // 관리자
            // 모든 회원 주문 정보 조회
            List<OrderVO> orderDetailsAdminList = orderDAO.getAllOrderDetails();
            request.setAttribute("orderDetailsAdminList", orderDetailsAdminList);
            request.setAttribute("isAdmin", true);
        } else { // 일반 회원
            String userid = String.valueOf(loginUser.getUserId());
            List<OrderVO> orderDetailsList = orderDAO.getOrderDetails(userid);
            request.setAttribute("orderDetailsList", orderDetailsList);
            request.setAttribute("isAdmin", false);
        }

        return "order/orderDetails";
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    public UpdateOrderStatusResponse updateStatus(@RequestParam String status,
                                                  @RequestParam int orderId) throws SQLException {

        int n = 0;
        boolean changeOrderStatus = false;

        n = orderDAO.updateOrderStatus(orderId, status);

        if( n == 1){
            changeOrderStatus = true;
        }
        else {
            changeOrderStatus = false;
        }

        UpdateOrderStatusResponse response = new UpdateOrderStatusResponse(changeOrderStatus);

        return response;
    }
}
