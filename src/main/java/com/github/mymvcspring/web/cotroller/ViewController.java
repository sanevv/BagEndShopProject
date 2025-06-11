package com.github.mymvcspring.web.cotroller;

import com.github.mymvcspring.repository.Image;
import com.github.mymvcspring.repository.user.MyUser;
import com.github.mymvcspring.service.ViewService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ViewService viewService;

    @GetMapping("/test.do")
    public String test(){
        return "test/test";
    }
    @GetMapping("/index.up")
    public String index(HttpServletRequest request){
        List<Image> imgList = viewService.getAllImages();
        request.setAttribute("imgList", imgList);
        return "index";
    }
    @GetMapping("/login/idFind.up")
    public String idFind(){
        return "login/id_find";
    }
    @GetMapping("/login/pwdFind.up")
    public String pwdFind(){
        return "login/pwd_find";
    }

    @GetMapping("/error.up")
    public void error(){

        throw new RuntimeException("에러가 발생했습니다.");
    }
    @GetMapping("/member/coinPurchaseTypeChoice.up")
    public String coinPurchaseTypeChoice(@RequestParam String userId, HttpServletRequest request) {
        //TODO: null 처리필요
        String loginId = ( (MyUser) request.getSession().getAttribute("loginUser") ).getUserId();
        boolean isNormal = userId != null && userId.equals(loginId);
                request.setAttribute("isNormal", isNormal);

        if(isNormal)
            return "member/coin_charge";
        request.setAttribute("message", "잘못된 접근입니다.");
        return "simple_error";
    }

    @GetMapping("/member/cash.up")
    public String cash(HttpServletRequest request, HttpServletRequest response) {
        response.setAttribute("message", "");


        return "";
    }
    @PostMapping("/member/cash.up")
    public String cashPost(@RequestParam String userId, HttpServletRequest request) {
        return "member/cash";
    }




}
