package com.github.semiprojectshop.web.seungho;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.semiprojectshop.repository.seungho.domail.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO_imple;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NoticeController {
	NoticeDAO ndao = new NoticeDAO_imple();
	NoticeVO nvo = new NoticeVO();
	@GetMapping("/notice")
	public String notice(HttpServletRequest request) {
		String notice_id = request.getParameter("notice_id");
		
		NoticeVO nvo = ndao.getNoticeID(notice_id);
		
		
		return ;
	}
	
	
}
