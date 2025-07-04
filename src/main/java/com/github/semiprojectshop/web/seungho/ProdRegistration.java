package com.github.semiprojectshop.web.seungho;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.semiprojectshop.repository.seungho.domain.CategoryVO;
import com.github.semiprojectshop.repository.seungho.domain.ProductVO;
import com.github.semiprojectshop.repository.seungho.model.CategoryDAO;
import com.github.semiprojectshop.repository.seungho.model.CategoryDAO_imple;
import com.github.semiprojectshop.repository.seungho.model.ProductDAO;
import com.github.semiprojectshop.service.sihu.StorageService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProdRegistration {
	private final CategoryDAO cdao;
	private final ProductDAO pdao;
	private final StorageService storageService;
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
			List<CategoryVO> cateList = cdao.getCategoryInfo();
			List<String> imageList = pdao.getImgPath(productId);
			
			request.setAttribute("imageList", imageList);
			request.setAttribute("cateList", cateList);
			request.setAttribute("pvo", pvo);
			System.out.println(imageList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "product/productUpdate";
	}
	
	@PostMapping("/deleteimg")
	@ResponseBody
	public String deleteImg(HttpServletRequest request) {
		String filename = request.getParameter("filename");
		String product_id = request.getParameter("product_id");
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("filename", filename);
		paramap.put("product_id", product_id);
		System.out.println(filename);
		int n = 0;
		
		if(filename != null) {
			
			try {
				n = pdao.deleteImg(paramap);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			JSONObject jsobj = new JSONObject();
			jsobj.put("n", n);

			return jsobj.toString();
		}
		
		
		return "";
	}
	
	@PostMapping("/updateProdInfoProdImg")
	@ResponseBody
	public String updateProdInfoProdImg(HttpServletRequest request,
										ProductVO pvo,
										@RequestParam("pimage1") MultipartFile thumbnailImg, // 썸네일
										@RequestParam (value = "files", required = false) List<MultipartFile> files) {
		System.out.println("이제 넣으면끝");
		System.out.println(files);
		System.out.println("확인용"+pvo.getProduct_id());
		String product_id = String.valueOf(pvo.getProduct_id());
		List<String> imgPath = new ArrayList<>();
		Path uploadDir = storageService.createFileDirectory("product", product_id);
		for(MultipartFile mf : files) {
			String imagePath = storageService.returnTheFilePathAfterTransfer(mf, uploadDir);
			imgPath.add(imagePath);
		}
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("product_id", pvo.getProduct_id());
		paramap.put("thumbnailImg", thumbnailImg);
		paramap.put("imgPath", imgPath);

		try {
			// int result = pdao.insertImg(paramap);
			
			int n = pdao.updateProduct(pvo);
			int result = pdao.insertImg(paramap);
			JSONObject jsobj = new JSONObject();
			jsobj.put("n", n);
			
			return jsobj.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		
		return "";
	}
	
	
	
	
	
	
}
