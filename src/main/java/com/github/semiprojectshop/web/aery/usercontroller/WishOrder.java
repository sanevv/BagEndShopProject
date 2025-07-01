package com.github.semiprojectshop.web.aery.usercontroller;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.aery.user.model.WishDAO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WishOrder extends AbstractController {

    private WishDAO wdao;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (wdao == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            wdao = ctx.getBean(WishDAO.class);
        }

        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("loginUser");

        // 로그인하지 않은 경우
        if (userObj == null) {
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/test/login.up");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            // 관심상품 주문 진행
            int ordersId = wdao.createWishOrder(userId, productId);

            // 주문 성공 시 주문 상세 페이지 이동
            request.setAttribute("message", "주문이 정상적으로 처리되었습니다.");
            request.setAttribute("loc", request.getContextPath() + "/"); // "/orderDetail.team1?ordersId=" + ordersId

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "주문 처리에 실패했습니다.");
            request.setAttribute("loc", "javascript:history.back()");
        }

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/views/aery/msg.jsp");
    }
}