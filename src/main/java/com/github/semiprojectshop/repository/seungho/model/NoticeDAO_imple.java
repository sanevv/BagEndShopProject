package com.github.semiprojectshop.repository.seungho.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.github.semiprojectshop.repository.seungho.domail.NoticeVO;

public class NoticeDAO_imple implements NoticeDAO {
	private DataSource ds;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 사용한 자원을 반납하는 close() 메소드 생성하기
    private void close() {
        try {
            if(rs    != null) {rs.close();     rs=null;}
            if(pstmt != null) {pstmt.close(); pstmt=null;}
            if(conn  != null) {conn.close();  conn=null;}
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }// end of private void close()---------------
    
    
	// 공지사항 인포 가져오기
	@Override
	public NoticeVO getNoticeID(String notice_id) throws SQLException {
		
		conn = ds.getConnection();
		
		try {
//			String sql =
			
		}finally {
			close();
		}
		
		
		return null;
	}

}
