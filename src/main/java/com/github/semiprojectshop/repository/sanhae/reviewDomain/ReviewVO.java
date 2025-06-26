package com.github.semiprojectshop.repository.sanhae.reviewDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class ReviewVO {

    private int reviewId;          // 리뷰번호
    private int userId;            // 사용자번호
    private int productId;         // 상품번호
    private String reviewContents; // 리뷰내용
    private int rating;            // 별점
    private String createdAt;      // 작성일

    // product_image 테이블 조인해서 사용할거임
    private String productImagePath; // 대표이미지 경로
}
