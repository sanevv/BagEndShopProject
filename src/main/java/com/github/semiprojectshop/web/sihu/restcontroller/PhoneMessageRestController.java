package com.github.semiprojectshop.web.sihu.restcontroller;

import com.github.semiprojectshop.service.sihu.PhoneMessageService;
import com.github.semiprojectshop.web.sihu.dto.sms.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/phone")
@RequiredArgsConstructor
public class PhoneMessageRestController {
    private final PhoneMessageService phoneMessageService;
    @PostMapping("/send")
    public boolean sendMessage(@RequestBody MessageRequest messageRequest) {
        // Implement SMS sending logic here
        phoneMessageService.sendMessage(messageRequest);
        return true;
    }
    @GetMapping("/verify")
    public boolean verifyMessage(@RequestParam String phoneNumber, @RequestParam String code) {
        // Implement SMS verification logic here
        return phoneMessageService.verifyMessage(phoneNumber, code);
    }
}
