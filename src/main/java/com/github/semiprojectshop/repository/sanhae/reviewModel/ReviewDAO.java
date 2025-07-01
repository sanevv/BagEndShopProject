package com.github.semiprojectshop.repository.sanhae.reviewModel;

import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;

import java.sql.SQLException;
import java.util.List;

public interface ReviewDAO {

    // 상품아이디로 리뷰리스트 조회하기
    List<ReviewVO> reviewList(int productId) throws SQLException;

    // 리뷰 등록하기
    ReviewVO addReview(ReviewVO reviewVO, String path);

    // 대표이미지 찾아오기
    String getProductImagePath(int productId);

    // 리뷰 등록한 사람 찾기
    String getReviewWriteUserid(int reviewId);

    // 리뷰 삭제하기
    ReviewVO deleteReview(ReviewVO reviewVO);


    // 리뷰 수정하기
    int updateReview(ReviewVO reviewVO);

    // 리뷰 내용 알아오기
    ReviewVO getReviewById(int reviewId);


    // 리뷰 이미지 넣어주기
    boolean updateReviewImage(int reviewId, String path);
}
