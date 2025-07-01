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
import com.github.semiprojectshop.config.web.SwaggerProperties;
import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;
import com.github.semiprojectshop.service.sihu.StorageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeWriteController {

	private final NoticeDAO ndao;
	private final StorageService storageService;
	NoticeVO nvo = new NoticeVO();


	@PostMapping("/abc")
	@ResponseBody
	public Map<String, Object> NoticeInsert(@RequestParam("thumbnail") MultipartFile thumbnail,
					            @RequestParam("title") String title,
					            @RequestParam("contents") String contents,
					            HttpServletRequest request) throws Exception {
		System.out.println("나왔어!");
		System.out.println(thumbnail.getOriginalFilename());
		Map<String, Object> json = new HashMap<>();
		/*
		 * String title = nvo.getTitle(); String contents = nvo.getContents();
		 */
		int result = 0;
		Path uploadDir = storageService.createFileDirectory("notice", title);
		String imagePath = storageService.returnTheFilePathAfterTransfer(thumbnail, uploadDir);
		
		
		if(title != null && contents != null) {
		Map<String, String> paramap = new HashMap<>();
		paramap.put("title", title);
		paramap.put("contents", contents);
		paramap.put("thumbnail", imagePath);
		result = ndao.insertNotice(paramap);
		
		}
		if(result == 1) {
	        json.put("result", 1);
	        json.put("message", "등록 성공");
	        json.put("url", "/notice/list.one");
			
		}
		return json;
	}

	@PostMapping("/write")
	public String NoticeWrite(HttpServletRequest request) {

		return "product/NoticeWrite";
	}

}
