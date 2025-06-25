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
}
