package com.github.semiprojectshop.web.sanhae.review;

import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAOImple;
import com.github.semiprojectshop.repository.sihu.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

// 웹 요청을 처리하는 컨트롤러
@RestController // 포스트맨으로 JSON값을 확인하고 싶을 때
@RequestMapping("/api/review")
@RequiredArgsConstructor // final이 붙은 필드를 생성자(Constructor)를 자동으로 생성해줌
public class ReviewController {


    private final ReviewDAO rvDAO;

    // 리뷰 조회하기
    @GetMapping("/list")
    public List<ReviewVO> getReviewList(@RequestParam("productId") int productId) throws SQLException {
        return rvDAO.reviewList(productId);
    }

    // 리뷰 작성하기
//    @PostMapping
//    public ReviewVO addReview() throws SQLException {
//        return  rvDAO.addReview();
//    }


}
