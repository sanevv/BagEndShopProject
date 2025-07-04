package com.github.semiprojectshop.web.seungho;



import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;
import com.github.semiprojectshop.service.sihu.StorageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeDAO ndao;
	private final StorageService storageService;
	NoticeVO nvo = new NoticeVO();

	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> NoticeUpdate(@RequestParam("thumbnail") MultipartFile thumbnail,
										HttpServletRequest request) throws SQLException {

		
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String notice_id =request.getParameter("notice_id");
		String prevThumbnail = request.getParameter("prevThumbnail");  // 기존 썸네일 받아오기
		String imagePath = null;	
		
		int result = 0;
		Map<String, Object> json = new HashMap<>();
	
	    if (thumbnail != null && !thumbnail.isEmpty()) {
	        Path uploadDir = storageService.createFileDirectory("notice", title);
	        imagePath = storageService.returnTheFilePathAfterTransfer(thumbnail, uploadDir);
	    } else {
	        imagePath = prevThumbnail;  // 새 이미지 없으면 기존 이미지 유지
	    }
		//System.out.println(title + contents + notice_id);
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("title", title);
		paraMap.put("contents", contents);
		paraMap.put("notice_id", notice_id);
		paraMap.put("thumbnail", imagePath);
		
		result = ndao.updateNotice(paraMap);
		if(result == 1) {
	        json.put("result", 1);
	        json.put("message", "등록 성공");
	        json.put("url", "/notice/list.one");
			
		}

		return json;
	}
	
	@PostMapping("/edit")
	public String goticeEdit(HttpServletRequest request) throws SQLException {
		System.out.println("나들어옴");
		String noticeID = request.getParameter("noticeID");
		
		NoticeVO nvo = ndao.getNoticeInfo(noticeID);
		
		request.setAttribute("nvo", nvo);
		//System.out.println(nvo);
		return "notice/NoticeUpdate";
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
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		request.setAttribute("loginUser", loginUser);
	    String notice_id = request.getParameter("notice_id");

	    try {
	        NoticeVO nvo = ndao.getNoticeInfo(notice_id);
	        
	        request.setAttribute("nvo", nvo);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "redirect:/error/500";
	    }

	    return "notice/Notice";
	}

}
