package com.github.semiprojectshop.repository.aery.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;

import lombok.RequiredArgsConstructor;

@Repository // 자동생성객체선언
@RequiredArgsConstructor // final 객체생성자 만들기
public class WishDAO_imple implements WishDAO {
	
	// DataSource 자동생성
	private final DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		try {
			if(rs    != null) {rs.close();	  rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn  != null) {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// 로그인한 사용자가 본인의 위시리스트를 조회
	@Override
	public List<ProductDetailVO> selectWishListByUserId(int userId) throws SQLException {
	    List<ProductDetailVO> list = new ArrayList<>();

	    try {
	        conn = ds.getConnection();

	        String sql = "SELECT w.wish_id, p.product_id, p.product_name, p.price, p.image "
	                   + "FROM wish_list w "
	                   + "JOIN product p ON w.product_id = p.product_id "
	                   + "WHERE w.user_id = ?";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, userId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            ProductDetailVO vo = new ProductDetailVO();
	            vo.setWish_id(rs.getInt("wish_id"));
	            vo.setProduct_id(rs.getInt("product_id"));
	            vo.setProduct_name(rs.getString("product_name"));
	            vo.setPrice(rs.getInt("price"));
	            vo.setImage(rs.getString("image")); // 이미지 경로 또는 파일명

	            list.add(vo);
	        }

	    } finally {
	        close();
	    }

	    return list;
	}		
		
		
		return null;
	}// end of private void close()---------------

	
}














