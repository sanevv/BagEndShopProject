package com.github.semiprojectshop.web.aery.usercontroller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAOImple;
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
            mdao = ctx.getBean(MemberDAO.class); // Bean 이름으로도 가능: ctx.getBean("memberDAO_imple", MemberDAO.class)
        }
    	
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        int result = mdao.deleteMember(email, password);

        if (result > 0) {
            HttpSession session = request.getSession();
            session.invalidate(); // 세션 종료 → 로그아웃

            request.setAttribute("message", "회원 탈퇴가 완료되었습니다.");
            //return "/WEB-INF/views/aery/user/withdrawSuccess.jsp";
        } else {
            request.setAttribute("message", "비밀번호가 일치하지 않습니다.");
           // return "/WEB-INF/views/aery/user/memberWithdraw.jsp";
        }
    }

}