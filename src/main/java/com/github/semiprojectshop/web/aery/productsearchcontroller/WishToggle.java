package com.github.semiprojectshop.web.aery.productsearchcontroller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.github.semiprojectshop.repository.aery.wish.model.WishDAO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WishToggle extends AbstractController {

    private WishDAO wishDAO;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (wishDAO == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            wishDAO = ctx.getBean(WishDAO.class);
        }

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); 
            JSONObject error = new JSONObject();
            error.put("error", "허용되지 않은 요청 방식입니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().print(error.toString());
            return;
        }

        // 로그인 여부 확인
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("loginUser");
        if (email == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            JSONObject error = new JSONObject();
            error.put("error", "로그인이 필요합니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().print(error.toString());
            return;
        }

        // productId
        String productIdStr = request.getParameter("productId");
        long productId;
        try {
            productId = Long.parseLong(productIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            JSONObject error = new JSONObject();
            error.put("error", "잘못된 상품 ID입니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().print(error.toString());
            return;
        }

        // 찜 토글 처리
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", email);
        paramMap.put("productId", productId);
        boolean isWished = wishDAO.toggleWish(paramMap);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", "success");
        jsonObj.put("wished", isWished);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(jsonObj.toString());
    }
}