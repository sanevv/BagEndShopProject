package com.github.semiprojectshop.web.seungho;

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
		
	
	@PostMapping("/write")
	public String NoticeWrite(HttpServletRequest request) {
		System.out.println("나다");
		
		
		return "product/NoticeWrite";
	}
	
	
}
