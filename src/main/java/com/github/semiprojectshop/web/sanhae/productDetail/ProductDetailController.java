package com.github.semiprojectshop.web.sanhae.productDetail;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import com.github.semiprojectshop.repository.sanhae.productModel.ProductDetailDAO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductDetailController {

    // 생성자, 필드, 메서드 가져오기
    @Autowired
    private ProductDetailDAO prdDAO;
    // GET으로 들어올 때
    // {productId} 는 경로 변수임
    @GetMapping("/detail/{productId}")
    // Model model은 Spring 에서 제공하는 View(jsp)페이지로 데이터를 전달에 필요한 인터페이스 객체이다.
    public String detail(@PathVariable int productId,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "sizePerPage", defaultValue = "5") int sizePerPage,
                        HttpSession session, Model model) throws SQLException {


        // 상세페이지 정보 가져오기
        ProductDetailVO prdVO = prdDAO.productDetail(productId);

        // 해당 상품 추가이미지 가져오기
        List<ProductDetailVO> productAddImageList = prdDAO.getProductImageList(productId);

        //System.out.println("productAddImageList.size() : " + productAddImageList.size());

        for (ProductDetailVO productAddImage : productAddImageList) {
            prdVO.setProductAddImagePath(productAddImage.getProductAddImagePath());

            //System.out.println("setProductAddImagePath : " + prdVO.getProductAddImagePath());
        }

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser != null) {
            int userId = loginUser.getUserId(); // 로그인한 사용자 ID

            model.addAttribute("userId", userId);
            //System.out.println("로그인 아이디 : "+ loginUser.getUserId());
        }
        //System.out.println("확인용 : " + product.getProductName());

        // JSP + Servlet 에서 사용하는 Request.setAttribute와 같은 듯?
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("prdVO", prdVO);
        model.addAttribute("productAddImageList", productAddImageList);


        return "product/productDetail";
    }

}
