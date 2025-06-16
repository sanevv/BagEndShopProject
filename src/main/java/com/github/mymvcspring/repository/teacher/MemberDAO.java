package com.github.mymvcspring.repository.teacher;

import com.github.mymvcspring.repository.teacher.vo.MemberVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface MemberDAO {
    //모든회원 가져오기
    List<MemberVO> getAllMembers() throws SQLException;

}








