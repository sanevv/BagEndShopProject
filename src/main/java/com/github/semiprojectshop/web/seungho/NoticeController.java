package com.github.semiprojectshop.web.seungho;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO_imple;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	NoticeDAO ndao = new NoticeDAO_imple();
	NoticeVO nvo = new NoticeVO();
	@GetMapping("/molla.one")
	public String notice(HttpServletRequest request) {
		String notice_id = request.getParameter("notice_id");
		
	try {
		NoticeVO nvo = ndao.getNoticeInfo("1");
	} catch (SQLException e) {

	}
	System.out.println(nvo);
		request.setAttribute("nvo", nvo);
		
		return "seungho/Notice";
	}
	
	
}
