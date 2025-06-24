package com.github.semiprojectshop.repository.seungho.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;

import lombok.RequiredArgsConstructor;



public class NoticeDAO_imple implements NoticeDAO {
	private final DataSource ds;
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
	public NoticeVO getNoticeInfo(String notice_id) throws SQLException {
		NoticeVO nvo = new NoticeVO();
		conn = ds.getConnection();
		
		try {
			String sql = " select notice_id, user_id, title, contents, thumbnail, created_at "
						+ " from notice "
						+ " where notice_id = to_numebr(?) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, notice_id);
			pstmt.executeQuery();
			
			if(rs.next()) {
				nvo = new NoticeVO();
				nvo.setNotice_id(rs.getString(1));
				nvo.setUserid(rs.getString(2));
				nvo.setTitle(rs.getString(3));
				nvo.setContents(rs.getString(4));
				nvo.setThumbnail(rs.getString(5));
				nvo.setCreated_at(rs.getString(6));
				
			}
			
		}finally {
			close();
		}
		
		
		return nvo;
	}

}
