package com.github.semiprojectshop.web.aery.usercontroller;

import java.util.List;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.aery.user.model.WishDAO;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http .HttpSession;

public class WishList extends AbstractController {

    private WishDAO wdao;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (wdao == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            wdao = ctx.getBean(WishDAO.class); // 또는 "wishDAO_imple", WishDAO.class
        }

        if (super.checkLogin(request)) {

            HttpSession session = request.getSession();
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

            // 로그인한 사용자 본인의 이메일로 관심상품 조회
            String email = loginUser.getEmail();

            List<ProductVO> wishProductList = wdao.selectWishListByUser(email);

            request.setAttribute("wishProductList", wishProductList);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/user/wishList.jsp");

        } else {

            // 로그인하지 않은 경우
            String message = "관심상품은 로그인 후 조회 가능합니다.";
            String loc = request.getContextPath() + "/test/login.up";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/msg.jsp");
            
        }
    }
}