package com.github.semiprojectshop.web.sihu.restcontroller.admin;

import com.github.semiprojectshop.service.sihu.admin.AdminService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.admin.Months3UsageAmountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final AdminService adminService;

    @GetMapping("/3months")
    public CustomResponse<List<Months3UsageAmountResponse>> getMonths3UsageAmount(@RequestParam(defaultValue = "3") long months){
        List<Months3UsageAmountResponse> responses = adminService.get3MonthsUsageAmount(months);

        return CustomResponse.ofOk("조회성공",responses);
    }
}
