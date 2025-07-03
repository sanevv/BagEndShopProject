package com.github.semiprojectshop.repository.sanhae.reviewModel;

import com.github.semiprojectshop.repository.sanhae.productDetailDomain.ProductDetailVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewCommentVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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



    // 리뷰리스트 구하기 근데 페이지네이션을 곁들인
    @Override
    public List<ReviewVO> reviewList(int productId, int page, int sizePerPage) {

        List<ReviewVO> rvList = new ArrayList<>();

        try {
            conn = ds.getConnection();

            String sql = " SELECT c.name AS userName, a.review_id, a.user_id, a.product_id, a.review_contents, a.rating, a.created_at, " +
                         " ( SELECT b.image_path " +
                         "   FROM product_image b " +
                         "   WHERE b.product_id = a.product_id  and b.thumbnail = 1 " +
                         " ) AS image_path " +
                         " FROM review a " +
                         " LEFT JOIN my_user c ON a.user_id = c.user_id " +
                         " WHERE product_id = ? " +
                         " ORDER BY created_at DESC " +
                         " OFFSET (?-1)*? ROW " +
                         " FETCH NEXT ? ROW ONLY ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            pstmt.setInt(2, page);
            pstmt.setInt(3, sizePerPage);
            pstmt.setInt(4, sizePerPage);

            rs = pstmt.executeQuery();

            while(rs.next()){
                ReviewVO rvo = new ReviewVO();

                rvo.setUserName(rs.getString("userName"));
                rvo.setReviewId(rs.getInt("review_id"));
                rvo.setUserId(rs.getInt("user_id"));
                rvo.setProductId(rs.getInt("product_id"));
                rvo.setReviewContents(rs.getString("review_contents"));
                rvo.setRating(rs.getInt("rating"));
                rvo.setCreatedAt(rs.getString("created_at"));
                rvo.setProductImagePath(rs.getString("image_path"));

                rvList.add(rvo);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return rvList;
    }


    // 리뷰 등록하기
    @Override
    public ReviewVO addReview(ReviewVO reviewVO, String path) {

        try {
            conn = ds.getConnection();

            String sql = " INSERT INTO review (user_id, product_id, review_contents, rating, review_image_path, created_at) " +
                         " VALUES (?, ?, ?, ?, ?, SYSDATE) ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewVO.getUserId());
            pstmt.setInt(2, reviewVO.getProductId());
            pstmt.setString(3, reviewVO.getReviewContents());
            pstmt.setInt(4, reviewVO.getRating());
            pstmt.setString(5, path);

            int result = pstmt.executeUpdate();

            if(result > 0) {
                return reviewVO;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return null;
    }



    // 대표이미지 찾아오기
    @Override
    public String getProductImagePath(int productId) {

        String result = null;

        try {
            conn = ds.getConnection();

            String sql = " SELECT image_path FROM product_image WHERE product_id = ? and thumbnail = 1 ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            rs.next();

            result = rs.getString(1);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return result;
    }


    // 리뷰 등록한 사람 찾기
    @Override
    public String getReviewWriteUserid(int reviewId) {

        String writeUserId = null;

        try {
            conn = ds.getConnection();

            String sql = " SELECT user_id FROM review WHERE review_id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                writeUserId = rs.getString("user_id");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return writeUserId;
    }

    // 리뷰 삭제하기
    @Override
    public ReviewVO deleteReview(ReviewVO reviewVO) {

        try {
            conn = ds.getConnection();

            String sql = " DELETE FROM review " +
                         " WHERE review_id = ? ";


            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewVO.getReviewId());

            int result = pstmt.executeUpdate();

            if(result > 0) {
                return reviewVO;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return null;
    }


    // 리뷰 수정하기
    @Override
    public int updateReview(ReviewVO reviewVO, String reviewImgPath) {
        int result = 0;

        try {
            conn = ds.getConnection();

            String sql = " UPDATE review " +
                         " SET review_contents = ?, rating = ?, review_image_path = ?, created_at = SYSDATE " +
                         " WHERE review_id = ? AND product_id = ? AND user_id = ? ";



            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reviewVO.getReviewContents());
            pstmt.setInt(2, reviewVO.getRating());
            pstmt.setString(3, reviewImgPath);
            pstmt.setInt(4, reviewVO.getReviewId());
            pstmt.setInt(5, reviewVO.getProductId());
            pstmt.setInt(6, reviewVO.getUserId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }

    // 리뷰 내용 알아오기
    @Override
    public ReviewVO getReviewById(int reviewId) {

        ReviewVO reviewVO = null;

        try {
            conn = ds.getConnection();

            String sql = " SELECT review_contents, rating, review_image_path FROM review " +
                         " WHERE review_id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                reviewVO = new ReviewVO();
                reviewVO.setReviewContents(rs.getString("review_contents"));
                reviewVO.setRating(rs.getInt("rating"));
                reviewVO.setReviewImagePath(rs.getString("review_image_path"));
            };

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }


        return reviewVO;
    }



    @Override
    public boolean updateReviewImage(int reviewId, String path) {
        try {
            conn = ds.getConnection();

            String sql = " UPDATE review SET review_image_path = ? WHERE review_id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, path);
            pstmt.setInt(2, reviewId);

            int result = pstmt.executeUpdate();
            return result > 0;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            close();
        }
    }



    // 기존 이미지 경로 조회
    @Override
    public String getReviewImagePath(int reviewId) {

        String result = null;

        try {
            conn = ds.getConnection();

            String sql = " SELECT review_image_path FROM review " +
                         " WHERE review_id = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);

            rs = pstmt.executeQuery();

            if(rs.next()){

                result = rs.getString("review_image_path");
            };

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return result;
    }

    // 해당 상품리스트 토탈 페이지 합계 구하기
    @Override
    public int getTotalPage(int productId, int sizePerPage) {

        int result = 0;

        try {
            conn = ds.getConnection();

            String sql = " SELECT ceil(COUNT(*)/?) AS totalPage " +
                         " FROM review " +
                         " WHERE product_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sizePerPage);
            pstmt.setInt(2, productId);
            rs = pstmt.executeQuery();

            rs.next();
            result = rs.getInt("totalPage");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return result;
    }

    // 리뷰코멘트여부 가져오기
    @Override
    public boolean getReviewComment(int reviewId) {

        boolean result = false;

        try {


            conn = ds.getConnection();

            String sql = " SELECT RC.comment_contents " +
                         " FROM review R JOIN review_comment RC " +
                         " ON R.review_id = RC.review_id " +
                         " WHERE RC.review_id = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            while(rs.next()){
                result = true;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return result;
    }

    // 리뷰코멘트 내용 가져오기
    @Override
    public ReviewCommentVO getReviewCommentInfo(int reviewId) {

        ReviewCommentVO rvcVO = null;

        try {
            conn = ds.getConnection();

            String sql = " SELECT RC.comment_contents, RC.review_comment_id " +
                         " FROM review R JOIN review_comment RC " +
                         " ON R.review_id = RC.review_id " +
                         " WHERE RC.review_id = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                rvcVO = new ReviewCommentVO();
                rvcVO.setReviewCommentId(rs.getInt("review_comment_id"));
                rvcVO.setCommentContents(rs.getString("comment_contents"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return rvcVO;
    }



}
