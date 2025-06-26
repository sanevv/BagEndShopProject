package com.github.semiprojectshop.repository.sanhae.reviewModel;

import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
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
@RequiredArgsConstructor // final이 붙은 필드를 생성자(Constructor)를 자동으로 생성해줌
public class ReviewDAOImple implements ReviewDAO {


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



    @Override
    public List<ReviewVO> reviewList(int productId) {

        List<ReviewVO> rvList = new ArrayList<>();

        try {
            conn = ds.getConnection();

            String sql = " SELECT review_id, user_id, product_id, review_contents, rating, to_char(created_at, 'yyyy-mm-dd hh24:mm:ss') AS created_at, " +
                         " ( SELECT b.image_path " +
                         "   FROM product_image b " +
                         "   WHERE b.product_id = a.product_id  and thumbnail = 1 " +
                         " ) AS image_path " +
                         " FROM review a " +
                         " WHERE product_id = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            while(rs.next()){
                ReviewVO rvo = new ReviewVO();

                rvo.setReviewId(rs.getInt("review_id"));
                rvo.setUserId(rs.getInt("user_id"));
                rvo.setProductId(rs.getInt("product_id"));
                rvo.setReviewContents(rs.getString("review_contents"));
                rvo.setRating(rs.getInt("rating"));
                rvo.setCreatedAt(rs.getString("created_at"));
                rvo.setProductImagePath(rs.getString("image_path"));

                rvList.add(rvo);
            }

            System.out.println("확인용"+ rvList);


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return rvList;
    }


}
