package com.github.mymvcspring.repository.teacher;

import com.github.mymvcspring.repository.teacher.vo.MemberVO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


public class MemberDAOImpl implements MemberDAO {
    private DataSource dataSource;


    @Override
    public List<MemberVO> getAllMembers() throws SQLException {
        return List.of();
    }
}
