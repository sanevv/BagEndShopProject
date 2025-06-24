package com.github.semiprojectshop.repository.sihu.product.cart;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartJpa extends JpaRepository<ProductCart, Long>, ProductCartJpaCustom {
    boolean existsByProductAndMyUser(Product product, MyUser myUser);
    long deleteByProductAndMyUser(Product product, MyUser myUser);
}
