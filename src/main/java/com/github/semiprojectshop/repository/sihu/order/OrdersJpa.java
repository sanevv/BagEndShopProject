package com.github.semiprojectshop.repository.sihu.order;

import com.github.semiprojectshop.web.sihu.dto.admin.Months3UsageAmountResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersJpa extends JpaRepository<Orders, Long>, OrdersJpaCustom {

}
