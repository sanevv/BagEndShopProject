package com.github.semiprojectshop.repository.sanhae.reviewModel;

import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewCommentVO;
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
public class ReviewCommentDAOImple implements ReviewCommentDAO {


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


    //
    @Override
    public String getCommentContents(int review_id) {

        String result = null;

        try {

            conn = ds.getConnection();

            String sql = " SELECT comment_contents FROM review_comment " +
                         " WHERE review_id = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, review_id);
            rs = pstmt.executeQuery();

            if(rs.next()){

                ReviewCommentVO rcVO = new ReviewCommentVO();
                rcVO.setComment_contents(rs.getString("comment_contents"));

                result = rcVO.getComment_contents();

                return result;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }
}
