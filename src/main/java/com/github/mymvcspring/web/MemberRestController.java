package com.github.mymvcspring.web;

import com.github.mymvcspring.repository.user.MyUser;
import com.github.mymvcspring.service.MemberService;
import com.github.mymvcspring.web.dto.LoginRequest;
import com.github.mymvcspring.web.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;
    @GetMapping("/check-id")
    public boolean duplicateCheckId(@RequestParam String userId){
        return memberService.duplicateCheckId(userId);
    }
    @GetMapping("/check-email")
    public boolean duplicateCheckEmail(@RequestParam String email){
        return memberService.duplicateCheckEmail(email);
    }
    @PostMapping("/register")
    public String registerUser(@RequestBody SignUpRequest signUpRequest) {
        signUpRequest.defaultSetting();
        memberService.registerLogic(signUpRequest);
        return "index";
    }
    @PostMapping("/login")
    public LoginRequest loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getPassword());
        return loginRequest;
    }


    @GetMapping("/my-test")
    public MyUser myTest(String userId){
        return memberService.getUserById(userId);
    }
    @GetMapping("/my-test2")
    public boolean myTest2(String userId){
        return memberService.checkId(userId);
    }

}
