package com.github.semiprojectshop.repository.sanhae.productModel;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
@RequiredArgsConstructor
public class ProductDetailDAOImple implements ProductDetailDAO {


    private final DataSource ds;
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
    }// end of private void close()---------------


    // 프론트에서 가져온 ProductId로 상세페이지 보여주기
    @Override
    public ProductDetailVO ProductDetail(int productId) throws SQLException {

        ProductDetailVO prdVO = null;

        try {
            conn = ds.getConnection();

            String sql = " SELECT product_name, product_info, product_contents, price, stock, product_size, matter," +
                         " ( SELECT b.image_path FROM product_image b WHERE b.product_id = a.product_id ) AS image_path " +
                         " FROM product a " +
                         " WHERE a.product_id = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                prdVO = new ProductDetailVO();
                prdVO.setProductName(rs.getString("product_name"));
                prdVO.setProductInfo(rs.getString("product_info"));
                prdVO.setProductContents(rs.getString("product_contents"));
                prdVO.setPrice(rs.getInt("price"));
                prdVO.setStock(rs.getInt("stock"));
                prdVO.setProductSize(rs.getString("product_size"));
                prdVO.setMatter(rs.getString("matter"));
                prdVO.setProductImagePath(rs.getString("image_path"));

                return prdVO;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return prdVO;
    }
}
