package com.github.semiprojectshop.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/am")//이게 만약 있다면 http://localhost:8080/am 이 공통적으로 들어가게된다.
public class ExampleController {

    //위의 @RequestMapping("/am")가 있다면 아래의 url은 http://localhost:8080/am/sanhae.one 이 된다.
    @GetMapping("/sanhae.one") // 아래의 request와 response는 사용하면 쓰고 필요없으면 생략가능.
    public String sanhae(HttpServletRequest request, HttpServletResponse response) {
        request.getContextPath();
        return "sanhae/sanhae";//views 폴더가 루트고 그안의 jsp파일  jsp빼고 작성
    }
    @PostMapping("/sanhae.one")
    public String sanhaePost(HttpServletRequest request, HttpServletResponse response) {
        request.getContextPath();
        return "sanhae/sanhaePost"; // views/sanhae/sanhaePost.jsp
    }

}
