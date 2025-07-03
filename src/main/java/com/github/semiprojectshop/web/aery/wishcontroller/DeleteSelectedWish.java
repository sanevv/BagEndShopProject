package com.github.semiprojectshop.web.aery.wishcontroller;

import com.github.semiprojectshop.repository.aery.wish.model.WishDAO;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.JSONObject;
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

        // 로그인 여부 확인
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("loginUser");

        JSONObject jsonObj = new JSONObject(); // JSON 응답 객체 준비

        if (userObj == null) {
            jsonObj.put("success", false);
            jsonObj.put("message", "로그인이 필요합니다.");
        } else {
            MemberVO loginUser = (MemberVO) userObj;
            int userId = loginUser.getUserId();

            // productId 들을 받아온다.
            String[] selectedProductIds = request.getParameterValues("productIds");

            if (selectedProductIds == null || selectedProductIds.length == 0) {
                jsonObj.put("success", false);
                jsonObj.put("message", "삭제할 상품이 선택되지 않았습니다.");
            } else {
                List<String> productIdList = Arrays.asList(selectedProductIds);
                int result = wdao.deleteSelectedWishes(userId, productIdList);

                if (result > 0) {
                    jsonObj.put("success", true);
                } else {
                    jsonObj.put("success", false);
                    jsonObj.put("message", "관심상품 삭제에 실패했습니다.");
                }
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObj.toString());
    }
}