package com.github.semiprojectshop.repository.sihu.product.cart;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCartJpa extends JpaRepository<ProductCart, Long>, ProductCartJpaCustom {
    boolean existsByProductAndMyUser(Product product, MyUser myUser);
    long deleteByProductInAndMyUser(List<Product> product, MyUser myUser);
}
