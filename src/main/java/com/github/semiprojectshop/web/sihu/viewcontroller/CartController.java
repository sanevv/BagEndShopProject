package com.github.semiprojectshop.web.sihu.viewcontroller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    @GetMapping("/temp")
    public String viewMyWish(){
        return "cart/my_cart_wish";
    }
    @GetMapping
    public String viewMyCart(HttpServletRequest request){
        request.setAttribute("cart", "나 카트얌");
        return "cart/my_cart";
    }
}
