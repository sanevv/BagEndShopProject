package com.github.semiprojectshop.web.sihu.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.semiprojectshop.config.DiscountConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Months3UsageAmountResponse {
    private int month;
    @Setter
    @JsonIgnore
    private long usageAmount;
    public long getUsageAmountDiscount() {

        return (long) (this.usageAmount * (1 - DiscountConstants.DISCOUNT_RATE));
    }
    public static Months3UsageAmountResponse fromMonth(int month){
        Months3UsageAmountResponse response = new Months3UsageAmountResponse();
        response.month = month;
        return response;
    }

}
