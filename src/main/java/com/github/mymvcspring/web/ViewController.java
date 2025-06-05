package com.github.mymvcspring.web;

import com.github.mymvcspring.repository.Image;
import com.github.mymvcspring.service.ViewService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/error.up")
    public void error(){

        throw new RuntimeException("에러가 발생했습니다.");
    }


}
