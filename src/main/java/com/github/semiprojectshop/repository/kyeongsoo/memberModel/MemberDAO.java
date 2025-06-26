package com.github.semiprojectshop.repository.kyeongsoo.memberModel;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;

import java.sql.SQLException;
import java.util.Map;

public interface MemberDAO {

    // 로그인하기 위한 것
    MemberVO login(Map<String, String> paramap) throws SQLException;

    // 이메일 찾기를 위한 것
    boolean findPhoneNum(Map<String, String> paramap) throws SQLException;

    // 아이디찾기에서 이메일과 날짜를 알려주는 것
    MemberVO knowTheEmailAndTheDate(Map<String, String> paramap) throws SQLException;

    // 비밀번호 찾기에서 이메일로 비밀번호를 찾는 것
    boolean findAPasswordByEmail(String email, String username, String userid) throws SQLException;

    // 이메일로 인증받기페이지에서 이메일을 가져오려고 하는 것
    MemberVO knowTheEmail(String email) throws SQLException;

    // 휴대폰 번호로 비밀번호 찾기 여부를 판단하는 메서드
    boolean judgmentCalledMobilePhoneNumber(String phoneNumber) throws SQLException;
}
