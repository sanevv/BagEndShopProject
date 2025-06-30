package com.github.semiprojectshop.web.sihu.viewcontroller;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Value("${portOne.primary}")
    private String portOnePrimary;
    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam("orderProductRequestsJson") String orderProductRequestsJson, Model model) {
        model.addAttribute("orderProductRequestsJson", orderProductRequestsJson);
        return "order/order_confirm";
    }
    @GetMapping("/payment")
    public String paymentPage(@RequestParam("paymentRequestJson") String paymentRequestJson, Model model, HttpSession session) {
        if(session.getAttribute("loginUser") == null)
            return "redirect:/test/login.up"; // 로그인 페이지로 리다이렉트


        model.addAttribute("paymentRequestJson", paymentRequestJson);

        MemberVO memberVO = (MemberVO) session.getAttribute("loginUser");
        model.addAttribute("email", memberVO.getEmail());
        model.addAttribute("name", memberVO.getName());
        model.addAttribute("phoneNumber", memberVO.getPhoneNumber());

        model.addAttribute("portOnePrimary", portOnePrimary);
        return "order/order_process";
    }
}
