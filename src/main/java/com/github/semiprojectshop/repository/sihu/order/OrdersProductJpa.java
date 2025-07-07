package com.github.semiprojectshop.repository.sihu.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersProductJpa extends JpaRepository<OrdersProduct, Long> {


}
