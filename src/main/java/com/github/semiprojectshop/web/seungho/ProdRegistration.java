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
			String thumbnail = pdao.getThumbnail(productId);
			request.setAttribute("thumbnail", thumbnail);
			request.setAttribute("imageList", imageList);
			request.setAttribute("cateList", cateList);
			request.setAttribute("pvo", pvo);
			//System.out.println(imageList);

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
	public Map<String, Object> updateProdInfoProdImg(HttpServletRequest request,
										ProductVO pvo,
										@RequestParam("product_contents_img") MultipartFile contents,
										@RequestParam("pimage1") MultipartFile thumbnailImg, // 썸네일
										@RequestParam (value = "files", required = false) List<MultipartFile> files) {
		Map<String, Object> resultMap = new HashMap<>();
		//System.out.println(files);
		String product_id = String.valueOf(pvo.getProduct_id());
		String thumbnailPath = "";
		String contentsPath = "";
		int n = 0;
		int result = 0;
		List<String> imgPath = new ArrayList<>();
		Path uploadDir = storageService.createFileDirectory("product", product_id);
		if (files != null && !files.isEmpty()) {
		    for (MultipartFile mf : files) {
		        if (!mf.isEmpty()) {
		            String imagePath = storageService.returnTheFilePathAfterTransfer(mf, uploadDir);
		            imgPath.add(imagePath);
		        }
		    }
		}
		String originContentsPath = request.getParameter("originContents");
		if(contents != null && !contents.isEmpty()) {
			contentsPath= storageService.returnTheFilePathAfterTransfer(contents, uploadDir);
		} else {
			contentsPath = originContentsPath;
		}
		
		// 썸네일 안바꾸면 그대로
		String originThumbnailPath = request.getParameter("originPimage1");

		if (thumbnailImg != null && !thumbnailImg.isEmpty()) {
		    thumbnailPath = storageService.returnTheFilePathAfterTransfer(thumbnailImg, uploadDir);
		} else {
		    thumbnailPath = originThumbnailPath; // 새 파일 없으면 기존 거 유지
		}
		
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("product_id", pvo.getProduct_id());
		System.out.println("업데이트 썸네일"+thumbnailPath);
		System.out.println("업데이트 상품" + product_id);
		paramap.put("thumbnailPath", thumbnailPath);
		
		
		paramap.put("imgPath", imgPath);
		
		try {
			// int result = pdao.insertImg(paramap);
			
			n = pdao.updateProduct(pvo, contentsPath);
			result = pdao.insertImg(paramap);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    resultMap.put("n", n);
	    resultMap.put("result", result);
	    return resultMap;
	}
	
	@PostMapping("/deleteProd")
	@ResponseBody
	public Map<String, Object> deleteProd(HttpServletRequest request){
		Map<String, Object> deleteProd = new HashMap<>();
		String product_id = request.getParameter("product_id");
		System.out.println("삭제할 상품번호 :" +product_id);
		int result = 0;
		
		try {
			result = pdao.deleteProd(product_id);
			System.out.println(result +"개 삭제댐");
			deleteProd.put("result", result);
			request.setAttribute("json", deleteProd);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return deleteProd;
	}
	
	
	
	
	
}
