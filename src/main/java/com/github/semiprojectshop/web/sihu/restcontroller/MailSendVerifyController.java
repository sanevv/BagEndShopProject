package com.github.semiprojectshop.web.sihu.restcontroller;

import com.github.semiprojectshop.service.EmailVerifyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailSendVerifyController {
    private final EmailVerifyService emailVerifyService;

    @PostMapping("/send")
    public boolean sendVerifyCodeToEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email, HttpServletRequest request) {
        return emailVerifyService.sendVerifyCodeToEmail(email, request.getSession());

    }

    @GetMapping("/verify")
    public boolean verifyEmail(@RequestParam @Email(message = "이메일 타입이 아닙니다.") String email, @RequestParam String code, HttpServletRequest request) {
        return emailVerifyService.verifyEmail(email, code, request.getSession());
    }
}
