package com.github.semiprojectshop.repository.sihu.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpa extends JpaRepository<Product, Long>, ProductJpaCustom {


}
