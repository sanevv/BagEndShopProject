package com.github.semiprojectshop.web.aery.usercontroller;

import java.sql.SQLException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MemberWithdrawController extends AbstractController {

    private MemberDAO mdao;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (mdao == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            mdao = ctx.getBean(MemberDAO.class); 
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            int result = mdao.withdrawMember(email, password);

            if (result == 1) {
                // 회원탈퇴 성공
                HttpSession session = request.getSession();
                session.invalidate(); // 로그아웃

                String message = "회원 탈퇴가 정상적으로 완료되었습니다.";
                String loc = request.getContextPath() + "/";

                request.setAttribute("message", message);
                request.setAttribute("loc", loc);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/views/aery/msg.jsp");
            } else {
                // 회원탈퇴 실패: 기존 비밀번호 입력
                String message = "기존 비밀번호를 입력하셔야 탈퇴가 가능합니다.";
                String loc = "javascript:history.back();";

                request.setAttribute("message", message);
                request.setAttribute("loc", loc);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/views/aery/msg.jsp");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "회원 탈퇴 중 오류가 발생했습니다.");
            request.setAttribute("loc", "javascript:history.back();");

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/views/aery/msg.jsp");
        }
    }
}