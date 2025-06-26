package com.github.semiprojectshop.web.aery.usercontroller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.aery.user.domain.MemberVO;
import com.github.semiprojectshop.repository.aery.user.model.MemberDAO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
public class MemberRegister extends AbstractController {

//	private MemberDAO mdao = new MemberDAO_imple();
	private MemberDAO mdao;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		//mdao 생성
		if (mdao == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            mdao = ctx.getBean(MemberDAO.class); // Bean 이름으로도 가능: ctx.getBean("memberDAO_imple", MemberDAO.class)
        }
		
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/views/aery/user/memberRegister.jsp");
			
		}
		
		else {
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String hp1 = request.getParameter("hp1");
			String hp2 = request.getParameter("hp2");
			String hp3 = request.getParameter("hp3");
			String zipCode = request.getParameter("zipCode");
			String address = request.getParameter("address");
			String addressDetails = request.getParameter("addressDetails");
			String phoneNumber  = hp1 + hp2 + hp3;
			
			MemberVO member = new MemberVO();
			member.setEmail(email);
			member.setPassword(password);
			member.setName(name);
			member.setPhoneNumber(phoneNumber);
			member.setZipCode(zipCode);
			member.setAddress(address);
			member.setDetailAddress(addressDetails);
			
			
			// #### 회원가입이 성공되어지면 자동으로 로그인 되도록 하겠다. #### //
			try {
				int n = mdao.registerMember(member);
				
				if(n==1) {
					Map<String, String> paraMap = new HashMap<>();
					paraMap.put("email", email);
					paraMap.put("password", password);
					
					MemberVO loginuser = mdao.login(paraMap);
					
					HttpSession session = request.getSession();
					session.setAttribute("loginuser", loginuser);
					
					String message = name + "님 회원가입에 감사드립니다.";
				    String loc = request.getContextPath()+"/index.team1";  
				    
				    request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				
				
			} catch(SQLException e) {
				    e.printStackTrace();
				
				    String message = "회원가입 실패ㅜㅜ";
				    String loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동하는 것. 
				    
				    request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
			}
			
			
		} // end of else------------------------------------
	 	
	}
	

}

