package com.github.semiprojectshop.repository.aery.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	// 로그인한 사용자가 본인의 관심상품을 조회
	@Override
	public List<ProductDetailVO> selectWishListByUser(String email) throws SQLException {
	    
		List<ProductDetailVO> list = new ArrayList<>();

	    try {
	    	conn = ds.getConnection();

	    	String sql = " SELECT u.user_id, u.email, p.product_id, p.product_name, p.price, "
                    + " ( SELECT i.image_path "
                    + "   FROM product_image i "
                    + "   WHERE i.product_id = p.product_id AND i.thumbnail = 1 "
                    + "   FETCH FIRST 1 ROWS ONLY) AS image_path, "
                    + " TO_CHAR(w.created_at, 'YYYY-MM-DD') AS created_at "
                    + " FROM wish w "
                    + " LEFT JOIN my_user u ON w.user_id = u.user_id "
                    + " LEFT JOIN product p ON w.product_id = p.product_id "
                    + " WHERE u.email = ? "
                    + " ORDER BY created_at DESC ";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, email);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	    	
	        	ProductDetailVO pdvo = new ProductDetailVO();
	        	
	            pdvo.setUserId(rs.getInt("user_id"));
	            pdvo.setProductId(rs.getInt("product_id"));
	            pdvo.setProductName(rs.getString("product_name"));
	            pdvo.setProductImagePath(rs.getString("image_path"));
	            pdvo.setCreatedAt(rs.getString("created_at"));
	            pdvo.setPrice(rs.getInt("price"));

	            list.add(pdvo);
	        }
	        
	    } finally {
	    	
	    	close();
	    }

	    return list;
	    
	}// end of public List<ProductDetailVO> selectWishListByUserId(int userId) throws SQLException---------------
	
	
	// 사용자가 해당 상품을 관심상품에 등록했는지 확인
	@Override
    public boolean exists(int userId, int productId) throws SQLException {
		
        boolean isExist = false;

        try {
            conn = ds.getConnection();

            String sql = "SELECT 1 FROM wish WHERE user_id = ? AND product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);

            rs = pstmt.executeQuery();
            isExist = rs.next();
            
        } finally {
        	
            close();
        }

        return isExist;
    }
	 

	// 관심상품 등록
	@Override
	public void insert(int userId, int productId) throws SQLException {
        try {
            conn = ds.getConnection();

            String sql = "INSERT INTO wish (user_id, product_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);

            pstmt.executeUpdate();
            
        } finally {
        	
            close();
        }
    }
	
	
	// 관심상품 제거
	@Override
    public void delete(int userId, int productId) throws SQLException {
        try {
            conn = ds.getConnection();

            String sql = "DELETE FROM wish WHERE user_id = ? AND product_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);

            pstmt.executeUpdate();
            
        } finally {
        	
            close();
        }
    }
	
	
	// 관심상품 등록 상태를 토글 (있으면 삭제, 없으면 추가)
	@Override
	public void toggle(int userId, int productId) throws SQLException {
	    if (exists(userId, productId)) {
	        delete(userId, productId);
	    } else {
	        insert(userId, productId);
	    }
	}

	
	// 관심상품을 장바구니에 담기
	@Override
	public void wishToCart(int userId, int productId) throws SQLException {
	    try {
	        conn = ds.getConnection();

	        // 이미 장바구니에 있는지 확인
	        String checkCart = "SELECT quantity "
	        		         + " FROM product_cart "
	        		         + " WHERE user_id = ? AND product_id = ? ";
	        
	        pstmt = conn.prepareStatement(checkCart);
	        pstmt.setInt(1, userId);
	        pstmt.setInt(2, productId);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            // 이미 장바구니에 해당 상품이 있다면 수량 1 증가
	            String updateCart = " UPDATE product_cart "
	            		          + " SET quantity = quantity + 1"
	            		          + " WHERE user_id = ? AND product_id = ? ";
	            pstmt = conn.prepareStatement(updateCart);
	            pstmt.setInt(1, userId);
	            pstmt.setInt(2, productId);
	            pstmt.executeUpdate();
	        } else {
	            // 장바구니에 해당 상품이 없다면 새로 추가
	            String insertSql = " INSERT INTO product_cart (user_id, product_id, quantity, created_at) "
	            		         + " VALUES (?, ?, 1, SYSDATE) ";
	            pstmt = conn.prepareStatement(insertSql);
	            pstmt.setInt(1, userId);
	            pstmt.setInt(2, productId);
	            pstmt.executeUpdate();
	        }
	        
	    } finally {
	    	
	        close();
	    }
	    
	}// end of public void moveToCart(int userId, int productId) throws SQLException--------------------------------

	
	// 관심상품을 기반으로 주문 생성 (orders + orders_product에 insert), orders_id 생성 반환
	@Override
	public int createWishOrder(int userId, int productId) throws SQLException {
		
	    int ordersId = -1; // 생성된 주문 ID 반환

	    try {
	        conn = ds.getConnection();
	        conn.setAutoCommit(false);

	        // orders 테이블에 insert (주문 생성)
	        String insertOrder = " INSERT INTO orders (user_id, created_at, status)"
	        		           + " VALUES (?, SYSDATE, 'DELIVERY') ";
	        pstmt = conn.prepareStatement(insertOrder, new String[] { "orders_id" }); // 자동 생성 키
	        pstmt.setInt(1, userId);
	        pstmt.executeUpdate();

	        // 생성된 orders_id 가져오기
	        rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            ordersId = rs.getInt(1);
	        } else {
	            throw new SQLException("주문 ID 생성 실패");
	        }

	        //  orders_product 테이블에 insert
	        String insertOrderProduct = " INSERT INTO orders_product (orders_id, product_id) "
	        		                     + " VALUES (?, ?) ";
	        pstmt = conn.prepareStatement(insertOrderProduct);
	        pstmt.setInt(1, ordersId);
	        pstmt.setInt(2, productId);
	        pstmt.executeUpdate();

	        conn.commit();

	    } catch (SQLException e) {
	        if (conn != null) conn.rollback();
	        throw e;
	    } finally {
	        conn.setAutoCommit(true);
	        close();
	    }

	    return ordersId;
	}
	
}

