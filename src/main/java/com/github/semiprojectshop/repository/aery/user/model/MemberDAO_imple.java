package com.github.semiprojectshop.repository.aery.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.github.semiprojectshop.repository.aery.user.domain.MemberVO;
import com.github.semiprojectshop.repository.aery.util.security.Sha256;

public class MemberDAO_imple implements MemberDAO {

	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public MemberDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기 // 스프링에서 관리 중으로 close() 사용 x
	private void close() {
		try {
			if(rs    != null) {rs.close();	  rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn  != null) {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close()---------------
	
	

	// 이메일 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다) 
	@Override
	public boolean emailDuplicateCheck(String email) throws SQLException {

		boolean isExists = false;
		
		try {
			  conn = ds.getConnection();
			  
			  String sql = " select email "
			  		     + " from my_user "
			  		     + " where email = ? ";
			  
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, email);
			  
			  rs = pstmt.executeQuery();
			  isExists = rs.next(); // 행이 있으면 true  (중복된 email) 
			                        // 행이 없으면 false (사용가능한 email) 
			
		} finally {
			  close();
		}
		
		return isExists;		
	}// end of public boolean emailDuplicateCheck(String email) throws SQLException-------
	
	

	// 회원가입을 해주는 메소드 (my_user 테이블에 insert)
	@Override
	public int registerMember(MemberVO member) throws SQLException {
		
		int result = 0;
		
		try {
			  conn = ds.getConnection();
			 
			  String sql = " insert into my_user(email, password, name, phone_number,"
			  		     + " zip_code, address, address_details, register_at, role_id)"
			  		     + " values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			  
			  pstmt = conn.prepareStatement(sql);
			  
			  pstmt.setString(1, member. getEmail());
			  pstmt.setString(2, Sha256.encrypt(member.getPassword()) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.  
			  pstmt.setString(3, member.getName());
			  pstmt.setString(4, member.getPhoneNumber());
			  pstmt.setInt(5, member.getZipCode());
			  pstmt.setString(6, member.getAddress());
			  pstmt.setString(7, member.getDetailAddress());
			  pstmt.setString(8, member.getRegisterAt());
			  pstmt.setInt(9, 1);
			  
			  result = pstmt.executeUpdate();
			  
		} finally {
			  close();
		}
		
		return result;
		
	}// end of public int registerMember(MemberVO member) throws SQLException-----------


	
	// 로그인 처리 
	@Override
	public MemberVO login(Map<String, String> paraMap) throws SQLException {
		
		MemberVO member = null;
		
		try {
			 conn = ds.getConnection();
			 
			 String sql = " select email, password, name, phone_number, "
				 		+ "        zip_code, address, address_details, register_at, role_id "
				 		+ " from my_user "
				 		+ " where email = ? and password = ? ";
			 
			 pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setString(1, paraMap.get("email"));
			 pstmt.setString(2, Sha256.encrypt(paraMap.get("password"))); // 비밀번호 SHA256 단방향 암호화
			 
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 
				 member = new MemberVO();
				 
				 member.setUserId(rs.getInt("user_id"));
		         member.setEmail(rs.getString("email"));
		         member.setName(rs.getString("name"));
		         member.setPhoneNumber(rs.getString("phone_number"));
		         member.setZipCode(rs.getInt("zip_code"));
		         member.setAddress(rs.getString("address"));
		         member.setDetailAddress(rs.getString("address_details"));
		         member.setRegisterAt(rs.getString("register_at"));
		         member.setRoleId(rs.getInt("role_id"));;
					 
				 }
				 
		} finally {
			close();
		}
		
		return member;
	}// end of public MemberVO login(Map<String, String> paraMap) throws SQLException-----


}

