package com.github.semiprojectshop.repository.sihu.order;

import com.github.semiprojectshop.web.sihu.dto.admin.Months3UsageAmountResponse;

import java.util.List;

public interface OrdersJpaCustom {
    List<Months3UsageAmountResponse> find3MonthsUsageAmount();
}
