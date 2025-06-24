package com.github.semiprojectshop.repository.kyeongsoo.model;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class DaoImple implements DaoCustom {
    private final DataSource ds;
    private final AES256 aes;
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

    @Override
    public ProductVO callTheSelectedValue(ProductVO pvo) throws SQLException {

        ProductVO result = null;

        try {
            conn = ds.getConnection();
            String query = " select product_name, product_info, price from product where product_name = '테라' ";

            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new ProductVO();
                result.setProductName(rs.getString("product_name"));
                result.setProductInfo(rs.getString("product_info"));
                result.setPrice(rs.getInt("price"));

            }
        } finally {
            close();
        }

        return result;
    }
}