package com.github.semiprojectshop.web.sanhae.viewcontroller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductDetailController {
    // GET으로 들어올 때
    @GetMapping("/detail")
    public String list() {
        return "product/productDetail";
    }
}
