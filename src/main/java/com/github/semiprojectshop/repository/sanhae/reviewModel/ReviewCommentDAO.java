package com.github.semiprojectshop.repository.sanhae.reviewModel;

import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewCommentVO;

import java.util.List;

public interface ReviewCommentDAO {


    // 댓글 내용 가져오기
    String getCommentContents(int review_id);

    // 관리자 댓글 작성하기
    ReviewCommentVO addReviewComment(ReviewCommentVO rvcVO);

    // 관리자 댓글 수정하기
    int updateReviewComment(ReviewCommentVO rvcVO);
}
