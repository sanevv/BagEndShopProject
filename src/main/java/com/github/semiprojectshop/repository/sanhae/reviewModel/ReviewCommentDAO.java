package com.github.semiprojectshop.repository.sanhae.reviewModel;

import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewCommentVO;

import java.util.List;

public interface ReviewCommentDAO {


    //
    String getCommentContents(int review_id);
}
