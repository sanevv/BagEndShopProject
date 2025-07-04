package com.github.semiprojectshop.repository.aery.wish;

import java.util.List;

import com.github.semiprojectshop.web.sihu.dto.product.wish.WishResponse;

public interface WishJpaCustom {
    List<WishResponse> findWishListPaging(long userId, int pageNo, int sizePerPage);
    int countWishByUserId(long userId);  // 총 개수 조회용
}