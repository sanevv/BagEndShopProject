package com.github.semiprojectshop.web.sihu.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
    @GetMapping("/error")
    public String errorPage(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        // 에러 페이지로 리다이렉트
        return "error";
    }
}
