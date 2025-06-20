package com.github.semiprojectshop.web.sanhae.test;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")//이게 만약 있다면 http://localhost:8080/am 이 공통적으로 들어가게된다.
public class testController {

    public String main() {
        return "index";//views 폴더가 루트고 그안의 jsp파일  jsp빼고 작성
    }

    @GetMapping("/productList.one")
    public String product() {
        return "product/productList";
    }
}
