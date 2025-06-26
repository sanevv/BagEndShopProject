package com.github.semiprojectshop.repository.sanhae.reviewModel;

import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;

import java.sql.SQLException;
import java.util.List;

public interface ReviewDAO {

    // 상품아이디로 리뷰리스트 조회하기
    List<ReviewVO> reviewList(int productId) throws SQLException;
}
