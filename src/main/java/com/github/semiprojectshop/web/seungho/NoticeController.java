package com.github.semiprojectshop.web.seungho;



import java.sql.SQLException;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeDAO ndao;
	NoticeVO nvo = new NoticeVO();

	@PostMapping("/list.one")
	public String noticeDelete(HttpServletRequest request) throws SQLException{
		String msg = "";
		String deleteId = request.getParameter("noticeID");
		
		int deleteOK = ndao.delete_notice(deleteId); 
		
		if(deleteOK == 1) {
			msg = "삭제성공";
			
		}
	
		
		return "redirect:/notice/list.one";
	}
	
	
	
	@GetMapping("/detail.one")
	public String notice(HttpServletRequest request) {
		String notice_id = request.getParameter("notice_id");

		try {
			nvo = ndao.getNoticeInfo(notice_id);

		} catch (SQLException e) {

		}

		request.setAttribute("nvo", nvo);

		return "product/Notice";
	}

}
