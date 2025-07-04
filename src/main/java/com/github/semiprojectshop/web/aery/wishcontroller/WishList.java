package com.github.semiprojectshop.web.aery.wishcontroller;

import java.util.List;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sihu.product.wish.WishJpaCustom;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;
import com.github.semiprojectshop.web.sihu.dto.product.wish.WishResponse;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WishList extends AbstractController {

    private WishJpaCustom wishRepository;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (wishRepository == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            wishRepository = (WishJpaCustom) ctx.getBean("wishJpaCustomImpl");
        }

        // 로그인 여부 확인
        if (super.checkLogin(request)) {

            // 로그인 유저 정보
            HttpSession session = request.getSession();
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
            long userId = loginUser.getUserId();

            // 페이지 번호
            String currentShowPageNo = request.getParameter("currentShowPageNo");
            if (currentShowPageNo == null || !currentShowPageNo.matches("\\d+")) {
                currentShowPageNo = "1";
            }

            int sizePerPage = 10;
            int pageNo = Integer.parseInt(currentShowPageNo);

            // 전체 관심상품 수
            int totalCount = wishRepository.countWishByUserId(userId);
            int totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

            if (pageNo > totalPage) {
                pageNo = 1;
                currentShowPageNo = "1";
            }

            // 현재 페이지에 해당하는 목록 조회
            List<WishResponse> wishProductList = wishRepository.findWishListPaging(userId, pageNo, sizePerPage);

            // 페이지 5 페이지 기준 블럭
            int blockSize = 5;
            int startPage = ((pageNo - 1) / blockSize) * blockSize + 1;
            int endPage = startPage + blockSize - 1;
            if (endPage > totalPage) endPage = totalPage;

            StringBuilder pageBar = new StringBuilder();

            // 이전 블럭
            if (startPage > 1) {
                pageBar.append("<li><a href='wishList.team1?currentShowPageNo=").append(startPage - 1).append("'>&lt;</a></li>");
            }

            for (int i = startPage; i <= endPage; i++) {
                if (i == pageNo) {
                    pageBar.append("<li class='active'><a href='#'>").append(i).append("</a></li>");
                } else {
                    pageBar.append("<li><a href='wishList.team1?currentShowPageNo=").append(i).append("'>").append(i).append("</a></li>");
                }
            }

            // 다음 블럭
            if (endPage < totalPage) {
                pageBar.append("<li><a href='wishList.team1?currentShowPageNo=").append(endPage + 1).append("'>&gt;</a></li>");
            }

            request.setAttribute("wishProductList", wishProductList);
            request.setAttribute("currentShowPageNo", pageNo);
            request.setAttribute("startPage", startPage);
            request.setAttribute("endPage", endPage);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("pageBar", pageBar.toString());

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/wish/wishList.jsp");

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