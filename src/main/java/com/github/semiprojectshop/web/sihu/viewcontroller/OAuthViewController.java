package com.github.semiprojectshop.web.sihu.viewcontroller;

import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthCodeParams;
import com.github.semiprojectshop.web.sihu.dto.oauth.request.OAuthLoginParams;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return "oauth/callback";



    }
}
