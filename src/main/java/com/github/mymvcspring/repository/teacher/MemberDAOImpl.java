package com.github.mymvcspring.repository.teacher;

import javax.sql.DataSource;
import java.sql.SQLException;



public class MemberDAOImpl implements MemberDAO {
    private DataSource dataSource;


    @Override
    public boolean idDuplicateCheck(String userId) throws SQLException {
        boolean isExists = false;
        return false;
    }
}
