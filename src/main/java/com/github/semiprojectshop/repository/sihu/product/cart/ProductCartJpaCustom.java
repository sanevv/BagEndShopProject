package com.github.semiprojectshop.repository.sihu.product.cart;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartRequest;

public interface ProductCartJpaCustom {

    long addQuantity(AddToCartRequest addToCartRequest, long loginUserId);
}
