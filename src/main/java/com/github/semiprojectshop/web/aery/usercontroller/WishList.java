package com.github.semiprojectshop.web.aery.usercontroller;

import java.util.List;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sihu.product.wish.WishJpaCustom;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;
/* import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO; */
import com.github.semiprojectshop.web.sihu.dto.product.wish.WishResponse;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http .HttpSession;

public class WishList extends AbstractController {

//  private WishDAO wdao;

	private WishJpaCustom wishRepository;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	    if (wishRepository == null) {
	    	ServletContext sc = request.getServletContext();
	    	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
	    //  wishRepository = ctx.getBean(WishJpaCustom.class); 
	    	wishRepository = (WishJpaCustom) ctx.getBean("wishJpaCustomImpl");		
	}

        if (super.checkLogin(request)) {

            HttpSession session = request.getSession();
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

            long userId = loginUser.getUserId();

            List<WishResponse> wishProductList = wishRepository.findMyWishListByUserId(userId);
            
            request.setAttribute("wishProductList", wishProductList);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/user/wishList.jsp");

        } else {
            String message = "관심상품은 로그인 후 조회 가능합니다.";
            String loc = request.getContextPath() + "/test/login.up";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/msg.jsp");
        }
    }
}