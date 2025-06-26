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
public class NoticeWriteController {
	private final NoticeDAO ndao;
	NoticeVO nvo = new NoticeVO();

	@PostMapping("/abc")
	public String NoticeInsert(NoticeVO nvo) throws SQLException {
		//System.out.println("나왔어!");
		String title = nvo.getTitle();
		String contents = nvo.getContents();
		String thumbnail = nvo.getThumbnail();
		int result = 0;
		if(title != null && contents != null) {
		Map<String, String> paramap = new HashMap<>();
		paramap.put("title", title);
		paramap.put("contents", contents);
		paramap.put("thumbnail", thumbnail);
		result = ndao.insertNotice(paramap);
		}
		if(result == 1) {
			return "redirect:/notice/list.one";
		}
		return "/";
	}

	@PostMapping("/write")
	public String NoticeWrite(HttpServletRequest request) {

		return "product/NoticeWrite";
	}

}
