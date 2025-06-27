package com.github.semiprojectshop.web.sanhae.review;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAOImple;
import com.github.semiprojectshop.repository.sihu.review.Review;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/review")
@RequiredArgsConstructor

//TODO: 나중에 주문결제 완료한 사람만 등록되게 해야함
public class ReviewController {

    private final ReviewDAO rvDAO;

    @GetMapping("/write")
    public String reviewReg(@RequestParam("productId") int productId,  HttpSession session, Model model){

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");


        // 대표이미지 찾아오기
        String productImagePath = rvDAO.getProductImagePath(productId);

        if (loginUser != null) {
            int userId = loginUser.getUserId(); // 로그인한 사용자 ID

            model.addAttribute("userId", userId);
            System.out.println("userId : "+ userId);
            //System.out.println("로그인 아이디 : "+ loginUser.getUserId());
            model.addAttribute("productImagePath", productImagePath);
        }


        return "review/reviewWrite";
    }
}
