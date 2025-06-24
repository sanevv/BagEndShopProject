package com.github.semiprojectshop.web.aery.usercontroller;


import org.json.JSONObject;

import com.github.semiprojectshop.repository.aery.user.model.MemberDAO;
import com.github.semiprojectshop.repository.aery.user.model.MemberDAO_imple;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmailDuplicateCheck extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
