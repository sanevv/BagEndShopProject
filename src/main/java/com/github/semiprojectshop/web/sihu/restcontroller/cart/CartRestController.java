package com.github.semiprojectshop.web.sihu.restcontroller.cart;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyViewException;
import com.github.semiprojectshop.service.sihu.product.CartService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartRequest;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartRestController {
    private final CartService cartService;

    @GetMapping
    public CustomResponse<List<CartListResponse>> requestCartList(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            throw CustomMyException.fromMessage("로그인 후 장바구니를 조회해주세요.");
        }
        int userId = ((MemberVO) session.getAttribute("loginUser")).getUserId();
         // 임시로 로그인한 회원 ID를 1로 설정
        List<CartListResponse> cartList = cartService.getAllCartListByUserId(userId);
        return CustomResponse.ofOk("장바구니 조회 성공" , cartList);
    }

    @PostMapping
    public CustomResponse<AddToCartResponse> requestAddToCart(@RequestBody AddToCartRequest addToCartRequest,
                                                              HttpSession session) {
        if (session.getAttribute("loginUser") == null)
            throw CustomMyViewException.fromMessage("로그인 후 장바구니에 상품을 추가해주세요.");
        long loginUserId = ((MemberVO) session.getAttribute("loginUser")).getUserId();
        AddToCartResponse response = cartService.addToCart(addToCartRequest, loginUserId);
        return CustomResponse.ofOk(response.getMessage(), response);
    }
    @DeleteMapping
    public CustomResponse<AddToCartResponse> requestDeleteFromCart(@RequestParam List<Long> productIds,
                                                                   HttpSession session) {
        if (session.getAttribute("loginUser") == null)
            throw CustomMyViewException.fromMessage("로그인 후 장바구니에서 상품을 삭제해주세요.");

        AddToCartResponse response = cartService.deleteFromCart(productIds, ((MemberVO) session.getAttribute("loginUser")).getUserId());
        return CustomResponse.ofOk(response.getMessage(), response);
    }
    @PutMapping
    public CustomResponse<AddToCartResponse> modifyQuantity(@RequestParam long productCartId, @RequestParam int quantity) {
        AddToCartResponse response = cartService.modifyQuantity(productCartId, quantity);
        return CustomResponse.ofOk(response.getMessage(), response);
    }
    @GetMapping("/count")
    public long viewMyCartCount(HttpSession session){
        if(session.getAttribute("loginUser") == null)
            return 0;
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        long loginUserId = member.getUserId();
        return cartService.getMyCartCount(loginUserId);
    }
}
