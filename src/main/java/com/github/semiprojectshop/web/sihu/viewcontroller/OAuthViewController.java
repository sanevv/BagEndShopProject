package com.github.semiprojectshop.web.sihu.viewcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthCodeParams;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthLoginParams;
import com.github.semiprojectshop.web.sihu.dto.oauth.response.OAuthSignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/oauth")
public class OAuthViewController {
    @GetMapping("/{provider}/callback")
    public String requestAuthByCode(
            @PathVariable OAuthProvider provider,
            @RequestParam String code,
            @RequestParam(required = false) String state,
            Model model
    ) {
        String redirectUri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .build()
                .toUriString();
        OAuthCodeParams codeParams = OAuthCodeParams.of(
                code,
                state,
                redirectUri
        );
        OAuthLoginParams params = OAuthLoginParams.fromCodeParams(codeParams, provider);
        model.addAttribute("params", params);
        model.addAttribute("provider", provider.name().toLowerCase());
        model.addAttribute("providerValue", provider.getValue());
        return "oauth/callback";
    }
    @PostMapping("/sign-up")
    public String oauthSignUpView(@ModelAttribute OAuthSignUpDto signUpDto, Model model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String signUpDtoJson = objectMapper.writeValueAsString(signUpDto);
            model.addAttribute("signUpDto", signUpDtoJson);
        } catch (JsonProcessingException e) {
            throw CustomMyException.fromMessage(e.getMessage());
        }

//        model.addAttribute("signUpDto", signUpDto);
        model.addAttribute("provider", signUpDto.getProvider().name());
        model.addAttribute("cart", "나 카트얌");
        return "oauth/sign_up";

    }
    @GetMapping("/oauth-test")
    public String test(Model model){
        model.addAttribute("cart", "나 카트얌");
        return "oauth/sign_up";
    }
}
