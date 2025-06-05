package com.github.mymvcspring.repository.teacher;

import java.sql.SQLException;

public interface MemberDAO {
    boolean idDuplicateCheck(String userId) throws SQLException;
}
