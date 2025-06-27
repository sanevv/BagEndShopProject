package com.github.semiprojectshop.web.kyeongsoo.restController.member;

import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class resetPasswordResponse {

    private final MemberDAO memberDAO;

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

}
