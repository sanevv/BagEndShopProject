package com.github.semiprojectshop.web.aery.wishcontroller;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.aery.wish.model.WishDAO;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WishToCart extends AbstractController {

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
            request.setAttribute("message", "로그인이 필요합니다.");
            request.setAttribute("loc", request.getContextPath() + "/test/login.up");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/msg.jsp");
            return;
        }

        MemberVO loginUser = (MemberVO) userObj;
        int userId = loginUser.getUserId();

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            // 관심상품에서 장바구니로 이동
            wdao.wishToCart(userId, productId);

            request.setAttribute("message", "장바구니에 담겼습니다.");
            request.setAttribute("loc", request.getContextPath() + "/cart");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "장바구니 담기에 실패했습니다.");
            request.setAttribute("loc", "javascript:history.back()");
        }

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/views/aery/msg.jsp");
    }
}