package com.github.semiprojectshop.service.sihu.admin;

import com.github.semiprojectshop.repository.sihu.order.OrdersJpa;
import com.github.semiprojectshop.web.sihu.dto.admin.Months3UsageAmountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final OrdersJpa ordersJpa;

    @Transactional(readOnly = true)
    public List<Months3UsageAmountResponse> get3MonthsUsageAmount(long months) {
        List<Months3UsageAmountResponse> myResult = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        long currentMonth = currentDate.getMonthValue();
        for(int i = 0; i < months; i++) {
            if (currentMonth - months + i <= 0) {
                // 월이 1월보다 작아지면, 12월로 변경
                myResult.add(Months3UsageAmountResponse.fromMonth(12 + (int) (currentMonth - months + i)));
                continue;
            }
            myResult.add(
                    Months3UsageAmountResponse.fromMonth((int) (currentMonth - months + i))
            );
        }
        List<Months3UsageAmountResponse> responses = ordersJpa.find3MonthsUsageAmount();

        responses.forEach(r ->{
            for (Months3UsageAmountResponse myResponse : myResult) {
                if (myResponse.getMonth() == r.getMonth()) {
                    myResponse.setUsageAmount(r.getUsageAmount());
                    break;
                }
            }
        });

        return myResult;
    }
}
