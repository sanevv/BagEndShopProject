package com.github.semiprojectshop.web.sihu.restcontroller.cart;

import com.github.semiprojectshop.service.sihu.CartService;
import com.github.semiprojectshop.web.sihu.dto.CustomResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartRequest;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartRestController {
    private final CartService cartService;


    @PostMapping
    public CustomResponse<AddToCartResponse> requestAddToCart(@RequestBody AddToCartRequest addToCartRequest) {
        AddToCartResponse response = cartService.addToCart(addToCartRequest);
        return CustomResponse.ofOk(response.getMessage(), response);
    }
    @DeleteMapping
    public CustomResponse<AddToCartResponse> requestDeleteFromCart(@RequestParam long productId) {
        AddToCartResponse response = cartService.deleteFromCart(productId);
        return CustomResponse.ofOk(response.getMessage(), response);
    }

}
