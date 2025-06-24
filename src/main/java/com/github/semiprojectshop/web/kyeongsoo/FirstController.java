package com.github.semiprojectshop.web.kyeongsoo;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO;
import com.github.semiprojectshop.repository.kyeongsoo.model.DaoCustom;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class FirstController {
    private final DaoCustom daoCustom;
    private final MemberDAO memberDAO;
    private AES256 aes256;


    @GetMapping("/index.up")
    public String index(HttpServletRequest request) {

        MemberVO loginuser = (MemberVO) request.getSession().getAttribute("loginUser");

        if(loginuser != null){

            System.out.println(loginuser.getName());

        }

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

    @SneakyThrows
    @GetMapping("/login.up")
    public String login(HttpServletRequest request, HttpServletResponse response){


        return "member/login";
    }

    @PostMapping("/login.up")
    public String loginPost(HttpServletRequest request){
        System.out.println("확인용");

        String userEmail = request.getParameter("userEmail");
        String pwd = request.getParameter("pwd");
        
        // 클라이언트 IP 가져오기
        String clientip = request.getRemoteAddr();

        String checkSaveId = request.getParameter("checkSaveId");

        Map<String, String> paramap = new HashMap<>();
        paramap.put("userEmail", userEmail);
        paramap.put("pwd", pwd);
        paramap.put("clientip", clientip);

        try {
            MemberVO loginUser = memberDAO.login(paramap);



            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);

            if(loginUser != null){
                return "redirect:/test/index.up";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return "member/login";
    }

    @GetMapping("/findEmail.up")
    public String findEmail (HttpServletRequest request, HttpServletResponse response){

        return "member/findEmail";
    }

    @GetMapping("/findEmailSuccess.up")
    public String findEmailSuccess (HttpServletRequest request, HttpServletResponse response){

        String name = request.getParameter("userName");
        String phoneNum = request.getParameter("phoneNumber");
        int cnt = 0;



        return "member/findEmailSuccess";
    }


}

























