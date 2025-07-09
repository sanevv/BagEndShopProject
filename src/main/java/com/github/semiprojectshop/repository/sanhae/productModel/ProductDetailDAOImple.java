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
import java.util.ArrayList;
import java.util.List;


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
    public ProductDetailVO productDetail(int productId) throws SQLException {

        ProductDetailVO prdVO = null;

        try {
            conn = ds.getConnection();

            String sql = " SELECT a.user_id AS user_id, a.product_id, a.product_name, " +
                         "    a.product_info, a.product_contents, a.price, a.stock, a.product_size, a.matter, " +
                         "    ( SELECT b.image_path FROM product_image b WHERE b.product_id = a.product_id AND b.thumbnail = 1 ) AS image_path " +
                         " FROM product a " +
                         " WHERE a.product_id = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {

                prdVO = new ProductDetailVO();

                prdVO.setUserId(rs.getInt("user_id"));
                prdVO.setProductId(rs.getInt("product_id"));
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


    // 상세페이지 추가이미지 리스트 불러오기
    @Override
    public List<ProductDetailVO> getProductImageList(int productId) {

        List<ProductDetailVO> productImageList = new ArrayList<>();

        try {
            conn = ds.getConnection();

            String sql = " SELECT PI.image_path AS productAddImagePath " +
                         " FROM product P JOIN product_image PI " +
                         " ON P.product_id = PI.product_id " +
                         " WHERE P.product_id = ? and PI.thumbnail = 0";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                ProductDetailVO prdVO = new ProductDetailVO();
                prdVO.setProductAddImagePath(rs.getString("productAddImagePath"));

                productImageList.add(prdVO);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return productImageList;
    }
}
