package com.github.semiprojectshop.repository.sihu.product.wish;

public interface WishJpaCustom {
    long steamingProductByUserId(long productId, long loginUserId);
}
