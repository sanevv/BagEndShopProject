package com.github.semiprojectshop.web.sihu.viewcontroller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public String list() {
        return "product/productList";
    }

    @GetMapping("/test")
    public String redirectToNoticeList() {
        System.out.println(System.getProperty("user.dir"));
        //D:\Develop\IntelliJ-Project\semi-project-shop
        return "redirect:/notice/list.one";
    }

}
