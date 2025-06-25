package com.github.semiprojectshop.web.aery.usercontroller;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.aery.user.model.MemberDAO;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmailDuplicateCheck extends AbstractController {

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
		
		if("POST".equals(method)) {
			
			String email = request.getParameter("email");
			
			boolean isExists = mdao.emailDuplicateCheck(email);
			
			JSONObject jsonObj = new JSONObject(); 
			jsonObj.put("isExists", isExists);
			
			String json = jsonObj.toString();
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/views/aery/jsonView.jsp");
			
		}// end of if("POST".equals(method))-------------------------
	}
}
