package com.github.semiprojectshop.repository.aery.user.model;
import java.sql.SQLException;
import java.util.Map;

import com.github.semiprojectshop.repository.aery.user.domain.MemberVO;


public interface MemberDAO {

	// 이메일 중복검사 (my_user 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다) 
	// ID 를 이메일로 대체함.
	boolean emailDuplicateCheck(String email) throws SQLException;

	// 회원가입을 해주는 메소드 (my_user 테이블에 insert)
	int registerMember(MemberVO user) throws SQLException;

	// 로그인 처리
	MemberVO login(Map<String, String> paraMap) throws SQLException;
	
}

