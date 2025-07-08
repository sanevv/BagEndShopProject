package com.github.semiprojectshop.web.aery.productsearchcontroller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductSearch {

    @GetMapping("/productSearch")
    public String moveSearchPage(@RequestParam(required = false) String keyword,
                                  HttpServletRequest request) {

        request.setAttribute("keyword", keyword); 
        return "/WEB-INF/views/aery/productSearch/productSearch.jsp"; 
        
    }
}