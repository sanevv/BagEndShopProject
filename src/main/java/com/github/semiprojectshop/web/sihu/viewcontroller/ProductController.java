package com.github.semiprojectshop.web.sihu.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public String list(Model model) {
        model.addAttribute("category", "all");
        return "product/productList";
    }
    @GetMapping("/{category}")
    public String listByCategory(@PathVariable String category, Model model) {
        model.addAttribute("category", category);
        return "product/productList";
    }

    @GetMapping("/test")
    public String redirectToNoticeList() {
        System.out.println(System.getProperty("user.dir"));
        //D:\Develop\IntelliJ-Project\semi-project-shop
        return "redirect:/notice/list.one";
    }

}
