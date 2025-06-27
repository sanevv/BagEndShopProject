package com.github.semiprojectshop.web.seungho;



import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

	@PostMapping("/update")
	public String NoticeUpdate(HttpServletRequest request) throws SQLException {
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String notice_id =request.getParameter("notice_id");
		//System.out.println(title + contents + notice_id);
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("title", title);
		paraMap.put("contents", contents);
		paraMap.put("notice_id", notice_id);
		
		int n = ndao.updateNotice(paraMap);
		//System.out.println(n);
		
		
		return "redirect:/notice/list.one";
	}
	
	@GetMapping("/update")
	public String goticeEdit(HttpServletRequest request) throws SQLException {
		//System.out.println("나들어옴");
		String noticeID = request.getParameter("noticeID");
		
		NoticeVO nvo = ndao.getNoticeInfo(noticeID);
		
		request.setAttribute("nvo", nvo);
		//System.out.println(nvo);
		return "product/NoticeUpdate";
	}
	
	@PostMapping("/delete")
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
	        NoticeVO nvo = ndao.getNoticeInfo(notice_id);
	        request.setAttribute("nvo", nvo);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "redirect:/error/500";
	    }

	    return "product/Notice";
	}

}
