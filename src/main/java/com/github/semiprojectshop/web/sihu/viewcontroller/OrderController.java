package com.github.semiprojectshop.web.sihu.viewcontroller;

import com.github.semiprojectshop.repository.aery.user.domain.MemberVO;
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
    public String paymentPage(@RequestParam("paymentRequestJson") String paymentRequestJson, Model model) {


        model.addAttribute("paymentRequestJson", paymentRequestJson);

        MemberVO memberVO = new MemberVO();//TODO: 로그인 로직 완성후 세션에서 꺼내오기
        model.addAttribute("email", "sihu2589@naver.com");
        model.addAttribute("name", "시후");
        model.addAttribute("phoneNumber", "010-2225-8999");

        model.addAttribute("portOnePrimary", portOnePrimary);
        return "order/order_process";
    }
}
