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
import java.util.Random;

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
        String test = request.getParameter("test");
        
        // 클라이언트 IP 가져오기
        String clientip = request.getRemoteAddr();

        String checkSaveId = request.getParameter("checkSaveId");
        request.setAttribute("checkSaveId", checkSaveId);

        Map<String, String> paramap = new HashMap<>();
        paramap.put("userEmail", userEmail);
        paramap.put("pwd", pwd);
        paramap.put("clientip", clientip);

        try {
            MemberVO loginUser = memberDAO.login(paramap);

            String id = loginUser.getEmail();

            request.setAttribute("id", id);
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
    public String findEmailSuccess (HttpServletRequest request, HttpServletResponse response) throws SQLException {

        MemberVO mvo = new MemberVO();

        String name = request.getParameter("name");
        String phoneNum = request.getParameter("phoneNum");
        int cnt = 0;

        Map<String, String> paramap = new HashMap<>();
        paramap.put("name", name);
        paramap.put("phoneNum", phoneNum);

        MemberVO findEmailAndDate = memberDAO.knowTheEmailAndTheDate(paramap);

        String email_1 = findEmailAndDate.getEmail();

        String[] emailParts = email_1.split("@");
        String emailPrefix = emailParts[0];
        String emailDomain = emailParts[1];

        StringBuilder sb = new StringBuilder();

        if (emailPrefix.length() <= 3) {
            // 앞자리 1개만 마스킹, 나머지는 그대로
            sb.append("*");
            if (emailPrefix.length() > 1) {
                sb.append(emailPrefix.substring(1));
            }
        } else {
            // 앞 3자리 유지, 나머지 마스킹
            sb.append(emailPrefix.substring(0, 3));
            for (int i = 3; i < emailPrefix.length(); i++) {
                sb.append("*");
            }
        }

        sb.append("@").append(emailDomain);
        String maskedEmail = sb.toString();

        if(findEmailAndDate != null){
            mvo.setEmail(maskedEmail);
            mvo.setRegisterAt(findEmailAndDate.getRegisterAt());
            cnt++;
        }
        String email = mvo.getEmail();
        String registerAt = mvo.getRegisterAt();

        request.setAttribute("email", email);
        request.setAttribute("registerAt", registerAt);
        request.setAttribute("cnt", cnt);



        return "member/findEmailSuccess";
    }

    @GetMapping("findPassword.up")
    public String findPassword (HttpServletRequest request, HttpServletResponse response){



        return "member/findPassword";
    }

    @GetMapping("receiveAuthenticationNumberByEmail")
    public String receiveAuthenticationNumberByEmail(HttpServletRequest request)  {

        String email = request.getParameter("email");
        request.setAttribute("email", email);
        return "member/receiveAuthenticationNumberByEmail";
    }

    @GetMapping("receiveAuthenticationNumberByPhone")
    public String receiveAuthenticationNumberByPhone(HttpServletRequest request)  {

         String phoneNumber = request.getParameter("phoneNumber");
         request.setAttribute("phoneNumber", phoneNumber);

        return "member/receiveAuthenticationNumberByPhone";
    }

    @GetMapping("resetPassword")
    public String resetPassword(HttpServletRequest request)  {

        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        request.setAttribute("email", email);
        request.setAttribute("phoneNumber", phoneNumber);


        return "member/resetPassword";
    }


}

























