package com.github.semiprojectshop.web.kyeongsoo;

import com.github.semiprojectshop.repository.kyeongsoo.domain.ProductVO;
import com.github.semiprojectshop.repository.kyeongsoo.model.DaoCustom;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class FirstController {
    private final DaoCustom daoCustom;


    @GetMapping("/kyeongsoo.up")
    public String index(HttpServletRequest request) {
        String message = "이건 테스트입니다";
        String username = "전경수";
        request.setAttribute("message", message);
        request.setAttribute("username", username);

        return "index";
    }
    @GetMapping("/kyeongsoo.down")
    public String index2(HttpServletRequest request) {

        ProductVO pvo = new ProductVO();

        try {
            ProductVO selectVo = daoCustom.callTheSelectedValue(pvo);
            String pName = selectVo.getProductName();
            String pInfo = selectVo.getProductInfo();
            int price = selectVo.getPrice();

            request.setAttribute("pName",pName);
            request.setAttribute("price",price);
            request.setAttribute("pInfo",pInfo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return "test/DAOtest";
    }
}

























