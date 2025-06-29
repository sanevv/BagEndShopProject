package com.github.semiprojectshop.web.sihu.restcontroller.cart;

import com.github.semiprojectshop.service.sihu.product.CartService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartRequest;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartRestController {
    private final CartService cartService;

    @GetMapping
    public CustomResponse<List<CartListResponse>> requestCartList() {
        //TODO: 로그인 기능이 구현되면 로그인한 회원의 장바구니만 조회하도록 수정
        long loginUserId = 1L; // 임시로 로그인한 회원 ID를 1로 설정
        List<CartListResponse> cartList = cartService.getAllCartListByUserId(loginUserId);
        return CustomResponse.ofOk("장바구니 조회 성공" , cartList);
    }

    @PostMapping
    public CustomResponse<AddToCartResponse> requestAddToCart(@RequestBody AddToCartRequest addToCartRequest) {
        AddToCartResponse response = cartService.addToCart(addToCartRequest);
        return CustomResponse.ofOk(response.getMessage(), response);
    }
    @DeleteMapping
    public CustomResponse<AddToCartResponse> requestDeleteFromCart(@RequestParam List<Long> productIds) {

        AddToCartResponse response = cartService.deleteFromCart(productIds);
        return CustomResponse.ofOk(response.getMessage(), response);
    }
    @PutMapping
    public CustomResponse<AddToCartResponse> modifyQuantity(@RequestParam long productCartId, @RequestParam int quantity) {
        AddToCartResponse response = cartService.modifyQuantity(productCartId, quantity);
        return CustomResponse.ofOk(response.getMessage(), response);
    }

}
