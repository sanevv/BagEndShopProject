package com.github.semiprojectshop.repository.sihu.product.wish;

import com.github.semiprojectshop.web.sihu.dto.product.wish.WishResponse;

import java.util.List;

public interface WishJpaCustom {
    long steamingProductByUserId(long productId, long loginUserId);

    List<WishResponse> findMyWishListByUserId(long userId);

	List<WishResponse> findWishListPaging(long userId, int pageNo, int sizePerPage);

	int countWishByUserId(long userId);
}
