package com.github.semiprojectshop.repository.seungho.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import com.github.semiprojectshop.config.oauth.client.GithubApiClient;
import com.github.semiprojectshop.repository.seungho.domain.CategoryVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryDAO_imple implements CategoryDAO {

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
	public List<CategoryVO> getCategoryInfo() throws SQLException {
		
		List<CategoryVO> cateList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = "select category_id, category_name from category";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryVO cvo = new CategoryVO();
				cvo.setCategory_id(rs.getString(1));
				cvo.setCategory_name(rs.getString(2));
				
				cateList.add(cvo);
			}

		}finally {
			close();
		}
		
		
		return cateList;
	} // end of public List<CategoryVO> getCategoryInfo() throws SQLException {} ---

}
