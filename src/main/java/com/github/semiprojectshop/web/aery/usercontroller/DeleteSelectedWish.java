package com.github.semiprojectshop.web.aery.usercontroller;

import com.github.semiprojectshop.repository.aery.user.model.WishDAO;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Arrays;
import java.util.List;

public class DeleteSelectedWish extends AbstractController {

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

        // 로그인 여부 확인
        if (userObj == null) {
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/test/login.up");
            return;
        }

        MemberVO loginUser = (MemberVO) userObj;
        int userId = loginUser.getUserId();

        
        // 선택된 상품 ID들 파라미터로 받기
        String[] selectedProductIds = request.getParameterValues("productIds");

        if (selectedProductIds == null || selectedProductIds.length == 0) {
            request.setAttribute("message", "삭제할 상품을 선택해주세요.");
            request.setAttribute("loc", "javascript:history.back()");
        } else {
            List<String> productIdList = Arrays.asList(selectedProductIds);
            int result = wdao.deleteSelectedWishes(userId, productIdList);

            if (result > 0) {
                request.setAttribute("message", "선택한 관심상품이 삭제되었습니다.");
                request.setAttribute("loc", request.getContextPath() + "/wish/wishlist.up");
            } else {
                request.setAttribute("message", "관심상품 삭제에 실패했습니다.");
                request.setAttribute("loc", "javascript:history.back()");
            }
        }

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/views/aery/msg.jsp");
    }
}
