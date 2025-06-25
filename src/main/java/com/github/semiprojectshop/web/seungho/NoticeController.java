package com.github.semiprojectshop.web.seungho;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO_imple;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeDAO ndao;
	NoticeVO nvo = new NoticeVO();

	@GetMapping("/detail.one")
	public String notice(HttpServletRequest request) {
		String notice_id = request.getParameter("notice_id");

		try {
			nvo = ndao.getNoticeInfo("1");

		} catch (SQLException e) {

		}

		request.setAttribute("nvo", nvo);

		return "seungho/Notice";
	}

}
