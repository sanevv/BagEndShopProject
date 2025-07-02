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
    private String reviewImagePath; // 리뷰 이미지

    private boolean isLoginReviewUser; // 로그인한 사람과 리뷰작성한 사람인지 알아올거

    // product_image 테이블 조인해서 사용할거임
    private String productImagePath; // 대표이미지 경로

    // 얘도 조인할거임
    private String userName; // 작성한 사용자이름

    // 달린 코멘트 유무
    boolean hasComment;



    // 사용자 이름 마스킹처리하기
    public static String getMaskName(String userName) {
        if (userName == null || userName.isEmpty() )  {
            return "";
        }

        int len = userName.length();

        if (len == 1) {
            return userName; // 한 글자는 그대로
        } else if (len == 2) {
            return userName.charAt(0) + "*"; // 두 글자는 앞 글자만 남김
        } else {
            // 가운데 글자만 *로 대체
            StringBuilder sb = new StringBuilder();
            sb.append(userName.charAt(0));
            sb.append("*".repeat(len - 2));
            sb.append(userName.charAt(len - 1));
            return sb.toString();
        }
    }
}
