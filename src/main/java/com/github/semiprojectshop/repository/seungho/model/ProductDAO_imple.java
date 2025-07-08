package com.github.semiprojectshop.repository.seungho.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import com.github.semiprojectshop.config.oauth.client.GithubApiClient;
import com.github.semiprojectshop.repository.seungho.domain.ProductVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDAO_imple implements ProductDAO {

	private final GithubApiClient githubApiClient;
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
					+ " from product " + " where product_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pvo.setProduct_id(rs.getLong(1));
				pvo.setProduct_name(rs.getString(2));
				pvo.setProduct_info(rs.getString(3));
				pvo.setProduct_contents(rs.getString(4));
				pvo.setPrice(rs.getLong(5));
				pvo.setStock(rs.getLong(6));
				pvo.setProduct_size(rs.getString(7));
				pvo.setMetter(rs.getString(8));
			}

		} finally {
			close();
		}

		return pvo;
	}

	@Override
	public List<String> getImgPath(String productId) throws SQLException {
		List<String> imgPath = new ArrayList<>();
		try {
			conn = ds.getConnection();
			String sql = "select image_path from product_image where product_id = ? and thumbnail = 0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				imgPath.add(rs.getString(1));

			}

		} finally {
			close();
		}

		return imgPath;
	}

	@Override
	public int deleteImg(Map<String, Object> paramap) throws SQLException {
		int n = 0;
		try {
			conn = ds.getConnection();
			String sql = "delete from product_image where image_path = ? and product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) paramap.get("filename"));
			pstmt.setString(2, (String) paramap.get("product_id"));
			n = pstmt.executeUpdate();

		} finally {
			close();
		}

		return n;
	}

	@Override
	public int updateProduct(ProductVO pvo, String contentsPath) throws SQLException {

		int n = 0;
		try {

			conn = ds.getConnection();
			String sql = "update product set product_name = ?, product_size = ?, matter = ?, stock = ?, price = ?, product_info = ?, product_contents = ?, category_id = ? where product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pvo.getProduct_name());
			pstmt.setString(2, pvo.getProduct_size());
			pstmt.setString(3, pvo.getMetter());
			pstmt.setLong(4, pvo.getStock());
			pstmt.setLong(5, pvo.getPrice());
			pstmt.setString(6, pvo.getProduct_info());
			pstmt.setString(7, contentsPath);
			pstmt.setString(8, pvo.getCategory_id());
			pstmt.setLong(9, pvo.getProduct_id());

			n = pstmt.executeUpdate();

		} finally {
			close();
		}

		return n;
	}

	// 업데이트한 정보 DB에 업데이트
	@Override
	public int insertImg(Map<String, Object> paramap) throws SQLException {
		int n = 0;

		try {
			conn = ds.getConnection();
			List<String> imgPath = (List<String>) paramap.get("imgPath");
			long productId = Long.parseLong(paramap.get("product_id").toString());
			for (int i = 0; i < imgPath.size(); i++) {
				String sql = "insert into product_image(product_id, image_path, thumbnail) values(?, ?, 0)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, productId);
				pstmt.setString(2, imgPath.get(i));
				n += pstmt.executeUpdate();

			}
			String thumbnailPath = (String) paramap.get("thumbnailPath");
			if (thumbnailPath != null && !thumbnailPath.trim().isEmpty()) {
				String sql = "update product_image set image_path = ? where product_id = ? and thumbnail = 1";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, thumbnailPath);
				pstmt.setLong(2, productId);
				int updated = pstmt.executeUpdate();
				if (updated == 0) {
			        // 없으면 insert
			        sql = "INSERT INTO product_image(product_id, image_path, thumbnail) VALUES(?, ?, 1)";
			        pstmt = conn.prepareStatement(sql);
			        pstmt.setLong(1, productId);
			        pstmt.setString(2, thumbnailPath);
			        n += pstmt.executeUpdate();
			    } else {
			        n += updated;
			    }
			}

		} finally {
			close();
		}

		return n;
	}

	@Override
	public String getThumbnail(String productId) throws SQLException {

		String thumbnail = "";
		try {
			conn = ds.getConnection();
						
			String sql = "select image_path from product_image where product_id = ? and thumbnail = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				thumbnail = rs.getString(1);
			}

		} finally {
			close();
		}

		return thumbnail;
	}

	@Override
	public int deleteProd(String product_id) throws SQLException {
		
		int result = 0;
		
		try {
	        conn = ds.getConnection();
	        conn.setAutoCommit(false); // 수동 커밋 설정

	        
	        String sql = "DELETE FROM product_image WHERE product_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, product_id);
	        pstmt.executeUpdate(); 
	        pstmt.close();

	        
	        sql = "DELETE FROM wish WHERE product_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, product_id);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	        sql = "DELETE FROM review WHERE product_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, product_id);
	        pstmt.executeUpdate();
	        pstmt.close();

	       
	        sql = "DELETE FROM product WHERE product_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, product_id);
	        int deleted = pstmt.executeUpdate(); 

	        if (deleted == 1) {
	            conn.commit();  // 부모 삭제 성공 시 커밋
	            result = 1;
	        } else {
	            conn.rollback(); // 부모 삭제 실패 시 롤백
	        }

	    } catch (SQLException e) {
	        if (conn != null) conn.rollback(); // 예외 시 롤백
	        throw e;
	    } finally {
	        close();
	    }

	    return result;
	}
}
