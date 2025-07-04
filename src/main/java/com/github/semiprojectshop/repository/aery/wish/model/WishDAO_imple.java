package com.github.semiprojectshop.repository.aery.wish.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WishDAO_imple implements WishDAO {

    private final DataSource ds;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private void close() {
        try {
            if (rs != null) { rs.close(); rs = null; }
            if (pstmt != null) { pstmt.close(); pstmt = null; }
            if (conn != null) { conn.close(); conn = null; }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
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
            if (exists(userId, productId)) return;
            String sql = "INSERT INTO wish (user_id, product_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } finally {
            close();
        }
    }

    
    // 관심상품 이미지 우측 x 버튼을 통한 관심상품 단일 삭제
    @Override
    public void deleteOne(int userId, int productId) throws SQLException {
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
            deleteOne(userId, productId);
        } else {
            insert(userId, productId);
        }
    }

    
    // 관심상품을 장바구니에 담기
    @Override
    public void wishToCart(int userId, int productId) throws SQLException {
        try {
            conn = ds.getConnection();

            String checkCart = "SELECT quantity FROM product_cart WHERE user_id = ? AND product_id = ?";
            pstmt = conn.prepareStatement(checkCart);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pstmt.close();
                String updateCart = "UPDATE product_cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";
                pstmt = conn.prepareStatement(updateCart);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, productId);
                pstmt.executeUpdate();
            } else {
                pstmt.close();
                String insertCart = "INSERT INTO product_cart (user_id, product_id, quantity, created_at) VALUES (?, ?, 1, SYSDATE)";
                pstmt = conn.prepareStatement(insertCart);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, productId);
                pstmt.executeUpdate();
            }
        } finally {
            close();
        }
    }

    
    // 관심상품을 기반으로 주문 생성 (orders + orders_product에 insert), orders_id 생성 반환
    @Override
    public int createWishToOrder(int userId, int productId) throws SQLException {
        int ordersId = -1;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            String insertOrder = "INSERT INTO orders (user_id, created_at, status) VALUES (?, SYSDATE, 'DELIVERY')";
            pstmt = conn.prepareStatement(insertOrder, new String[]{"orders_id"});
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                ordersId = rs.getInt(1);
            } else {
                throw new SQLException("주문 ID 생성 실패");
            }

            pstmt.close();
            String insertOrderProduct = "INSERT INTO orders_product (orders_id, product_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertOrderProduct);
            pstmt.setInt(1, ordersId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();

            conn.commit(); // 성공 시 커밋

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true); // 자동 커밋 복원
            close();
        }
        return ordersId;
    }

    
    // 관심상품에서 선택 상품 삭제(다중 포함)
    @Override
    public int deleteSelectedWishes(int userId, List<String> productIdList) {
        int result = 0;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);

            String placeholders = String.join(",", productIdList.stream().map(id -> "?").toArray(String[]::new));
            String sql = "DELETE FROM wish WHERE user_id = ? AND product_id IN (" + placeholders + ")";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            for (int i = 0; i < productIdList.size(); i++) {
                pstmt.setInt(i + 2, Integer.parseInt(productIdList.get(i)));
            }
            result = pstmt.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException rollbackEx) { rollbackEx.printStackTrace(); }
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    
    // 관심상품 목록 페이징 조회
    @Override
    public List<ProductVO> selectWishListPaging(Map<String, String> paraMap) throws SQLException {
    	
        List<ProductVO> list = new ArrayList<>();

        try {
            conn = ds.getConnection();

            String email = paraMap.get("email");
            int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
            int sizePerPage = 10; // 고정

            String sql = " SELECT p.product_id, p.product_name, p.price, " 
                       + "       TO_CHAR(w.created_at, 'YYYY-MM-DD') AS created_at "
                       + " FROM wish w "
                       + " JOIN my_user u ON w.user_id = u.user_id "
                       + " JOIN product p ON w.product_id = p.product_id "
                       + " WHERE u.email = ? "
                       + " ORDER BY w.created_at DESC "
                       + " OFFSET (? - 1) * ? ROWS FETCH NEXT ? ROWS ONLY ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setInt(2, currentShowPageNo);
            pstmt.setInt(3, sizePerPage);
            pstmt.setInt(4, sizePerPage);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductVO pdvo = new ProductVO();
                pdvo.setProductId(rs.getInt("product_id"));
                pdvo.setProductName(rs.getString("product_name"));
                pdvo.setPrice(rs.getInt("price"));
                pdvo.setCreatedAt(rs.getString("created_at"));
                list.add(pdvo);
            }

        } finally {
            close();
        }

        return list;
    }
    
    
    // 관심상품 총 개수 조회
    @Override
    public int getTotalWishCount(String email) throws SQLException {
        int count = 0;
        try {
            conn = ds.getConnection();

            String sql = " SELECT COUNT(*) FROM wish w JOIN my_user u ON w.user_id = u.user_id WHERE u.email = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } finally {
            close();
        }

        return count;
    }

}
