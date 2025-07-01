package com.github.semiprojectshop.web.seungho;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainPageNotice {
	private final NoticeDAO ndao;
	NoticeVO nvo = new NoticeVO();
	
	@GetMapping("/")
	public String Mainnotice(HttpServletRequest request) throws SQLException {
		List<NoticeVO> nvoList = new ArrayList<>();
		nvoList = ndao.selectMainPageNotice();
		
		request.setAttribute("nvoList", nvoList);
		
		
	return "index";
	}
}
