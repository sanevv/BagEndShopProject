package com.github.semiprojectshop.web.seungho;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.semiprojectshop.repository.seungho.domain.CategoryVO;
import com.github.semiprojectshop.repository.seungho.domain.ProductVO;
import com.github.semiprojectshop.repository.seungho.model.CategoryDAO;
import com.github.semiprojectshop.repository.seungho.model.CategoryDAO_imple;
import com.github.semiprojectshop.repository.seungho.model.ProductDAO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProdRegistration {
	private final CategoryDAO cdao;
	private final ProductDAO pdao;
	@PostMapping("/register")
	public String registration(HttpServletRequest request) {
		
		try {
			List<CategoryVO> cateList = cdao.getCategoryInfo();
			request.setAttribute("cateList", cateList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return"product/productRegister";
	}
	
	
	@PostMapping("/update")
	public String productUpdate(HttpServletRequest request) {
		ProductVO pvo = new ProductVO();
		String productId = request.getParameter("productId");
		// System.out.println("확인용" + productId);
		try {
			pvo = pdao.productDetailInfo(productId);
			request.setAttribute("pvo", pvo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "product/productUpdate";
	}
	
	
	
	
	
	
}
