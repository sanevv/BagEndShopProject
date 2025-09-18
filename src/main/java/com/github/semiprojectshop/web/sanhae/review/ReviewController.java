package com.github.semiprojectshop.web.sanhae.review;

import com.github.semiprojectshop.config.FtpProperties;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAOImple;
import com.github.semiprojectshop.repository.sihu.review.Review;
import com.github.semiprojectshop.service.sihu.StorageService;
import com.github.semiprojectshop.web.support.FtpUrlHelper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;


@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewDAO rvDAO;
    private final FtpUrlHelper ftpUrlHelper;

    @GetMapping("/detail/{productId}/{reviewId}")
    public String reviewDetail(@PathVariable String productId, @PathVariable String reviewId, Model model){

        // 대표이미지 찾아오기
        String productImagePath = rvDAO.getProductImagePath(Integer.parseInt(productId));

        // 리뷰 내용 조회
        ReviewVO review = rvDAO.getReviewById(Integer.parseInt(reviewId));
        if(review != null){
            // 리뷰 이미지 경로 내부 경로 제거
            review.setReviewImagePath(ftpUrlHelper.toPublicPath(review.getReviewImagePath()));
        }

        model.addAttribute("ftpHost", ftpUrlHelper.normalizedHost());
        model.addAttribute("reviewVO", review);
        model.addAttribute("reviewId", reviewId);
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

        if(review != null){
            // 리뷰 이미지 경로 내부 경로 제거
            review.setReviewImagePath(ftpUrlHelper.toPublicPath(review.getReviewImagePath()));
        }

        model.addAttribute("ftpHost", ftpUrlHelper.normalizedHost());
        model.addAttribute("userId", userId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("productId", productId);
        model.addAttribute("productImagePath", productImagePath);
        model.addAttribute("reviewVO", review);

        return "review/reviewUpdate";
    }

}
