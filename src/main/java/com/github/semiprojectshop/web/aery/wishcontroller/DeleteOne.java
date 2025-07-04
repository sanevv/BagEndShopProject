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

public class DeleteOne extends AbstractController {

    private WishDAO wdao;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (wdao == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            wdao = ctx.getBean(WishDAO.class);
        }

        JSONObject jsonObj = new JSONObject();

        // 로그인 여부 확인
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("loginUser");

        if (userObj == null) {
            jsonObj.put("success", false);
            jsonObj.put("message", "로그인이 필요합니다.");
            sendJson(response, jsonObj, 401);
            return;
        }

        MemberVO loginUser = (MemberVO) userObj;
        int userId = loginUser.getUserId();

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            wdao.deleteOne(userId, productId);

            jsonObj.put("success", true);
            jsonObj.put("message", "삭제 완료");
            sendJson(response, jsonObj, 200);

        } catch (Exception e) {
            jsonObj.put("success", false);
            jsonObj.put("message", "삭제 실패");
            sendJson(response, jsonObj, 500);
        }
    }

    private void sendJson(HttpServletResponse response, JSONObject json, int status) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(json.toString());
    }
}
