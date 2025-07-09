package com.github.semiprojectshop.web.kyeongsoo.restController.member;

import com.github.semiprojectshop.config.encryption.Sha256;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.repository.sihu.user.MyUserJpa;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class resetPasswordResponse {
    private final MyUserJpa myUserJpa;
    private final MemberDAO memberDAO;
    @GetMapping("/exist-email")
    public boolean existEmail(@RequestParam String email) {
        return !myUserJpa.existsByEmail(email);
    }
    @GetMapping("/exist-phone")
    public boolean existPhone(@RequestParam String phoneNumber) {
        return !myUserJpa.existsByPhoneNumber(phoneNumber);
    }
    @GetMapping("/exist-pwd")
    public boolean existPassword(@RequestParam String password) {
        return !myUserJpa.existsByPasswordAndEmail(password, "email");
    }

    @PutMapping("/resetPassword")
    public boolean resetPassword(@RequestParam String newPassword,
                                               @RequestParam String phoneNumber,
                                               @RequestParam String email) throws SQLException {
        // 비밀번호 재설정 로직을 여기에 구현

        boolean isUpdated = false;
        if (email.contains("@")) {
            // 이메일인 경우
            // TODO: 이메일 기준 사용자 조회 및 비밀번호 업데이트 로직

            // 이메일로 비밀번호 변경하기
            int n = memberDAO.changePasswordByEmail(email, newPassword);

            if (n == 1){
                isUpdated = true;
            }
            else{
                return false;
            }

        }
        if (phoneNumber.startsWith("010")) {
            // 전화번호인 경우
            // TODO: 전화번호 기준 사용자 조회 및 비밀번호 업데이트 로직

            // 전화번호로 비밀번호 변경하기
            int n = memberDAO.changePasswordByPhoneNumber(phoneNumber, newPassword);

            if (n == 1){
                isUpdated = true;
            }
            else{
                return false;
            }

        }


        return isUpdated;
    }

    @PostMapping("/memberOneChange")
    public boolean memberOneChange(HttpSession session,
                                   @RequestParam String password,
                                   @RequestParam String name,
                                   @RequestParam String hp1,
                                   @RequestParam String hp2,
                                   @RequestParam String hp3,
                                   @RequestParam String zipCode,
                                   @RequestParam String address,
                                   @RequestParam String addressDetails) throws SQLException {

        boolean isUpdated = false;

        String email = ((MemberVO) session.getAttribute("loginUser")).getEmail();

        String phoneNumber = hp1 + hp2 + hp3;

        Map<String, String> paramap = new HashMap<>();
        paramap.put("email", email); // 수정 금지, 조회용으로만 사용
        paramap.put("password", password);
        paramap.put("phoneNumber", phoneNumber);
        paramap.put("name", name);
        paramap.put("zipCode", zipCode);
        paramap.put("address", address);
        paramap.put("addressDetails", addressDetails);

        int n = memberDAO.memberOneChange(paramap);
        if(n == 1){
            isUpdated = true;
        }

        return isUpdated;
    }

    @PostMapping("/login")
    public boolean login(@RequestParam(required = false) String userEmail, @RequestParam(required = false, value = "loginPwd") String loginPwd, HttpSession session) throws SQLException {
        MyUser myUser = myUserJpa.findByEmailFetchJoin(userEmail);
        String passwordEncrypted = Sha256.encrypt(loginPwd);
        if (myUser == null || !myUser.getPassword().equals(passwordEncrypted)) return false;
        if(!myUser.isEnabled()){
            return false;
        }

        MemberVO loginUser = new MemberVO();
        loginUser.setEmail(myUser.getEmail());
        loginUser.setRoleId(Math.toIntExact(myUser.getRoles().getRoleId()));
        loginUser.setPhoneNumber(myUser.getPhoneNumber());
        loginUser.setName(myUser.getName());
        loginUser.setZipCode(myUser.getZipCode());
        loginUser.setAddress(myUser.getAddress());
        loginUser.setAddressDetails(myUser.getAddressDetails());
        loginUser.setRegisterAt(myUser.getRegisterAt().toString());
        loginUser.setUserId(Math.toIntExact(myUser.getUserId()));
        // 로그인 성공 시 세션에 사용자 정보 저장

        session.setAttribute("loginUser", loginUser);


        return true;
    }



}
