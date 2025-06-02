package com.github.mymvcspring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/test.do")
    public String test(){
        return "test/test";
    }
}
