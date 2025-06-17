package com.github.mymvcspring.web.cotroller;

import com.github.mymvcspring.service.AdminService;
import com.github.mymvcspring.web.dto.CustomResponse;
import com.github.mymvcspring.web.dto.PaginationDto;
import com.github.mymvcspring.web.dto.member.MemberDetailResponse;
import com.github.mymvcspring.web.dto.member.MemberListResponse;
import com.github.mymvcspring.web.dto.member.SearchConditions;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final AdminService adminService;

    @GetMapping("/member")
    public CustomResponse<PaginationDto<MemberListResponse>> searchMemberList(

            @ModelAttribute SearchConditions searchConditions,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size
    ) {
        if (searchConditions.getSearchValue() != null && !searchConditions.getSearchValue().isBlank())
            searchConditions.covertSearchType();


        return adminService.searchMemberList(searchConditions, page-1, size)
                .filter(list -> !list.getItems().isEmpty())
                .map(memberList -> CustomResponse.ofOk("회원 목록 조회 성공", memberList))
                .orElseGet(() -> CustomResponse.emptyDataOk("회원 목록이 없습니다."));
    }
    @GetMapping("/member/{userId}")
    public CustomResponse<MemberDetailResponse> getMemberInfo(@PathVariable String userId) {
        return adminService.getMemberInfo(userId)
                .map(member -> CustomResponse.ofOk("회원 정보 조회 성공", member))
                .orElseGet(() -> CustomResponse.emptyDataOk("해당 회원이 존재하지 않습니다."));
    }
}
