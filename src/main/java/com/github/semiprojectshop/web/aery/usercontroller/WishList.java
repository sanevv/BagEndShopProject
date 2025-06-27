package com.github.semiprojectshop.web.aery.usercontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.semiprojectshop.repository.aery.user.domain.MemberVO;
import com.github.semiprojectshop.repository.aery.user.model.WishDAO;
import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http .HttpSession;


public class WishList extends AbstractController {

	@Autowired
	private WishDAO wishDAO;
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (super.checkLogin(request)) {

            HttpSession session = request.getSession();
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

            int userId = Integer.parseInt(request.getParameter("userId"));

            // 로그인한 사용자 자신의 위시리스트를 조회하려는 경우
            if (loginUser.getUserId() == userId) {

                List<ProductDetailVO> wishProductList = wishDAO.selectWishListByUserId(userId);

                request.setAttribute("wishProductList", wishProductList);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/views/aery/user/wishList.jsp");

            } else {
                // 다른 사용자의 위시리스트를 조회하려는 경우
                String message = "다른 사용자의 위시리스트는 조회할 수 없습니다.";
                String loc = "javascript:history.back()";

                request.setAttribute("message", message);
                request.setAttribute("loc", loc);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/views/aery/msg.jsp");
            }

        } else {
        	
            // 로그인하지 않은 경우
            String message = "위시리스트는 로그인 후 조회 가능합니다.";
            String loc = request.getContextPath() + "/login.up";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/msg.jsp");
        }
    }
}
	
	