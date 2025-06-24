package com.github.semiprojectshop.web.seungho;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.semiprojectshop.config.SwaggerProperties;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/abc")
public class hshtestController {

	@GetMapping("/test.down")
	public String def(HttpServletRequest request) {
		
		
		
		
		return "seungho/DetailProduct";
	}

}
