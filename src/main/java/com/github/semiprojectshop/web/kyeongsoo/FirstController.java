package com.github.semiprojectshop.web.kyeongsoo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class FirstController {

    @GetMapping("/kyeongsoo.up")
    public String index(HttpServletRequest request) {
        String message = "이건 테스트입니다";
        String username = "전경수";
        request.setAttribute("message", message);
        request.setAttribute("username", username);

        return "index";
    }
    @PostMapping("/kyeongsoo.up")
    public String index2(){

        return "index";
    }
}
