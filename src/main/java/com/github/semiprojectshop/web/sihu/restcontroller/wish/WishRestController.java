package com.github.semiprojectshop.web.sihu.restcontroller.wish;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.service.sihu.product.WishService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.product.wish.WishResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class WishRestController {
    private final WishService wishService;

    @GetMapping
    public CustomResponse<List<WishResponse>> getMyWishList(HttpSession session) {
        if(session.getAttribute("loginUser") == null)
            throw CustomMyException.fromMessage("로그인이 필요합니다.");

        long userId = ((MemberVO) session.getAttribute("loginUser")).getUserId();
        List<WishResponse> responseList = wishService.getMyWishList(1L);
        return CustomResponse.ofOk("위시리스트 조회 성공", responseList);

    }



}
