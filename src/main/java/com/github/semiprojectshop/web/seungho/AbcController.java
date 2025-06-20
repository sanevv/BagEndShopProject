package com.github.semiprojectshop.web.seungho;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.semiprojectshop.config.SwaggerProperties;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/abc")
public class AbcController {

    private final SwaggerProperties swaggerProperties;

    AbcController(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }
	
	@GetMapping("/def")
	public String abc(HttpServletRequest request, @RequestParam String name) {
		// System.out.println("들어옴 "+ name);
		
		
		
		request.setAttribute("name", name);
		return "product/productList_test";
	}
	@GetMapping("/test.down")
	public String def() {
		System.out.println("좆됏음");
		return "seungho/TestHSH";
	}

}
