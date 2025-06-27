package com.github.semiprojectshop.web.sanhae.review;

import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController // 포스트맨으로 JSON값을 확인하고 싶을 때
@RequestMapping("/api/review")
@RequiredArgsConstructor // final이 붙은 필드를 생성자(Constructor)를 자동으로 생성해줌
public class ReviewRestController {

    private final ReviewDAO rvDAO;

    // 리뷰 조회하기
    @GetMapping("/list")
    public List<ReviewVO> getReviewList(@RequestParam("productId") int productId) throws SQLException {
        return rvDAO.reviewList(productId);
    }

    // 리뷰 작성하기
    // @RequestBody : HTTP 요청의 본문을 Java 객체로 변환해줌
    // @ResponseBody : 자바 객체를 JSON으로 변환해서 클라이언트에 던져줌
    // ==> @RestController 선언이 되어 있다면 생략가능
    @PostMapping("/write")
    public ReviewVO addReview(@RequestBody ReviewVO reviewVO ) throws SQLException {

        // addReview 메소드에서 처리한 결과 값을 ReviewVO insertReview 담아줌
        ReviewVO insertReview = rvDAO.addReview(reviewVO);

        return insertReview;
    }

}
