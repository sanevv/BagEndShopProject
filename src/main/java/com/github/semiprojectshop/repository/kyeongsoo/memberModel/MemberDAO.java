package com.github.semiprojectshop.repository.kyeongsoo.memberModel;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;

import java.sql.SQLException;
import java.util.Map;

public interface MemberDAO {
    MemberVO login(Map<String, String> paramap) throws SQLException;
}
