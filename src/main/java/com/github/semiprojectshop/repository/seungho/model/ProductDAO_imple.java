package com.github.semiprojectshop.repository.seungho.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.github.semiprojectshop.repository.seungho.domain.ProductVO;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class ProductDAO_imple implements ProductDAO {
	private final DataSource ds;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;


	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close()---------------
	@Override
	public ProductVO productDetailInfo(String productId) throws SQLException {
			ProductVO pvo = new ProductVO();
		try {
			conn = ds.getConnection();
			String sql = " select product_id, product_name, product_info, product_contents, price, stock, product_size, matter"
					   + " from product "
					   + " where product_id = ? "; 
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pvo.setProduct_id(rs.getLong(1));
				pvo.setProduct_name(rs.getString(2));
				pvo.setProduct_info(rs.getString(3));
				pvo.setProduct_contents(rs.getString(4));
				pvo.setPrice(rs.getLong(5));
				pvo.setStock(rs.getLong(6));
				pvo.setProduct_size(rs.getString(7));
				pvo.setMetter(rs.getString(8));
			}
			
			
		}finally {
			close();
		} 
		
		
		
		return pvo;
	}

}
