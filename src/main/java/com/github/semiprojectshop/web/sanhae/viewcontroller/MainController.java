package com.github.semiprojectshop.web.sanhae.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//웹 요청을 처리하는 컨트롤러
@Controller
public class MainController {
    public String main() {
        return "index";
    }
}
