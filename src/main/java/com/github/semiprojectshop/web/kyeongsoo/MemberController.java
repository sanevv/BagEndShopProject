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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class MemberController {
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
        // System.out.println("확인용");

        String userEmail = request.getParameter("userEmail");
        String pwd = request.getParameter("pwd");

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

            // 사용자 객체가 null이 아닌지 확인
            if (loginUser != null) {

                String id = loginUser.getEmail();
                request.setAttribute("id", id);
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", loginUser);

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

        String referer = request.getHeader("Referer");
        if (referer == null) {
            // 페이지를 홈화면 으로 보낸다.
            return "redirect:/";
        }

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

        String referer = request.getHeader("Referer");
        if (referer == null) {
            // 페이지를 홈화면 으로 보낸다.
            return "redirect:/";
        }

        String email = request.getParameter("email");
        request.setAttribute("email", email);
        return "member/receiveAuthenticationNumberByEmail";
    }

    @GetMapping("receiveAuthenticationNumberByPhone")
    public String receiveAuthenticationNumberByPhone(HttpServletRequest request)  {

        String referer = request.getHeader("Referer");
        if (referer == null) {
            // 페이지를 홈화면 으로 보낸다.
            return "redirect:/";
        }

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

    @GetMapping("myPage")
    public String myPage(HttpServletRequest request) {

        String referer = request.getHeader("Referer"); // referer가 없으면 메인페이지로 이동

        if (referer == null) {
            // referer == null 은 웹브라우저 주소창에 URL 을 직접 입력하고 들어온 경우이다.
            return "redirect:/test/index.up";
        }

        HttpSession session = request.getSession();
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        request.setAttribute("loginUser", loginUser);

        return "include/mypageMenu";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.invalidate(); // 세션을 무효화하여 로그아웃 처리

        return "redirect:/test/index.up"; // 로그아웃 후 메인 페이지로 리다이렉트
    }

    @GetMapping("memberOneChange")
    public String memberOneChange(HttpServletRequest request) throws SQLException {

        HttpSession session = request.getSession();
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        request.setAttribute("loginUser", loginUser);

        String phoneNumber = loginUser.getPhoneNumber();

        String hp1 = phoneNumber.substring(0,3);
        String hp2 = phoneNumber.substring(3,7);
        String hp3 = phoneNumber.substring(7);

        request.setAttribute("hp1", hp1);
        request.setAttribute("hp2", hp2);
        request.setAttribute("hp3", hp3);
        request.setAttribute("phoneNumber", phoneNumber);
        request.setAttribute("email", loginUser.getEmail());




        return "member/memberOneChange"; // 회원 정보 수정 페이지로 이동
    }




}

























