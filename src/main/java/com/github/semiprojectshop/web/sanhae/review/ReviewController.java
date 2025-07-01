package com.github.semiprojectshop.web.sanhae.review;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAOImple;
import com.github.semiprojectshop.repository.sihu.review.Review;
import com.github.semiprojectshop.service.sihu.StorageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;


@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
//TODO: 나중에 주문결제 완료한 사람만 등록되게 해야함
public class ReviewController {

    private final ReviewDAO rvDAO;


    @GetMapping("/detail/{productId}/{reviewId}")
    public String reviewDetail(@PathVariable String productId, @PathVariable String reviewId, Model model){

        // 대표이미지 찾아오기
        String productImagePath = rvDAO.getProductImagePath(Integer.parseInt(productId));

        System.out.println("productImagePath : " + productImagePath);
        System.out.println("reviewId : " + reviewId);

        // 리뷰 내용 조회
        ReviewVO review = rvDAO.getReviewById(Integer.parseInt(reviewId));

        //System.out.println("등록한 리뷰이미지  : " + review.getReviewImagePath());

        model.addAttribute("reviewVO", review);
        model.addAttribute("productImagePath", productImagePath);



        return "review/reviewDetail";
    }
    @GetMapping("/write")
    public String reviewReg(@RequestParam("productId") int productId,  HttpSession session, Model model){

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        // 대표이미지 찾아오기
        String productImagePath = rvDAO.getProductImagePath(productId);

        if(loginUser == null) {
            return "redirect:/test/login.up";
        }

        int userId = loginUser.getUserId(); // 로그인한 사용자 ID

        model.addAttribute("userId", userId);
        System.out.println("userId : "+ userId);

        model.addAttribute("productImagePath", productImagePath);

        return "review/reviewWrite";
    }

    @GetMapping("/update")
    public String reviewUpdate(@RequestParam("productId") int productId, @RequestParam("reviewId") int reviewId, HttpSession session, Model model){

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/test/login.up";
        }

        // 로그인한 사용자 ID
        int userId = loginUser.getUserId();
        String productImagePath = rvDAO.getProductImagePath(productId);

        // 리뷰 내용 조회
        ReviewVO review = rvDAO.getReviewById(reviewId);



        model.addAttribute("userId", userId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("productId", productId);
        model.addAttribute("productImagePath", productImagePath);
        model.addAttribute("reviewVO", review);

        return "review/reviewUpdate";
    }
}
