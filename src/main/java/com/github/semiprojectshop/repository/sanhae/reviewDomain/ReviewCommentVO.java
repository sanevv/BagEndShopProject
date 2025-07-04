package com.github.semiprojectshop.repository.sanhae.reviewDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class ReviewCommentVO {


    //REVIEW_COMMENT_ID NOT NULL NUMBER
    //USER_ID           NOT NULL NUMBER
    //REVIEW_ID         NOT NULL NUMBER
    //COMMENT_CONTENTS  NOT NULL VARCHAR2(255)

    private int reviewCommentId; // 댓글번호
    private int reviewId;          // 리뷰번호
    private int userId;            // 사용자번호
    private String commentContents; // 댓글내용

}
