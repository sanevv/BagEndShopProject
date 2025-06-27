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
import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoticeDAO_imple implements NoticeDAO {


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

	// 공지사항 인포 가져오기
	@Override
	public NoticeVO getNoticeInfo(String notice_id) throws SQLException {
		NoticeVO nvo = new NoticeVO();

		try {
			conn = ds.getConnection();

			String sql = " select notice_id, user_id, title, contents, thumbnail, to_char(created_at, 'yyyy-mm-dd') as created_at "
					+ " from notice " + " where notice_id = to_number(?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, notice_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				nvo = new NoticeVO();
				nvo.setNotice_id(rs.getString(1));
				nvo.setUserid(rs.getString(2));
				nvo.setTitle(rs.getString(3));
				nvo.setContents(rs.getString(4));
				nvo.setThumbnail(rs.getString(5));
				nvo.setCreated_at(rs.getString(6));

			}

		} finally {
			close();
		}

		return nvo;
	} // end of public NoticeVO getNoticeInfo(String notice_id) throws SQLException {}
		// ---

	// 페이지네이션에 들어갈 notice 8개씩 가져오기
	@Override
	public List<NoticeVO> noticeList(Map<String, String> paraMap) throws SQLException {
		List<NoticeVO> noticeList = new ArrayList<>();
		try {
			conn = ds.getConnection();

			String sql = "select notice_id, user_id, title, contents, thumbnail, to_char(created_at, 'yyyy-mm-dd') as created_at"
					+ " from notice " + " order by created_at desc " + " OFFSET (?-1)*? ROW "
					+ "	FETCH NEXT 8 ROW ONLY";

			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage"));

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, currentShowPageNo);
			pstmt.setInt(2, sizePerPage);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeVO nvo = new NoticeVO();

				nvo.setNotice_id(rs.getString(1));
				nvo.setUserid(rs.getString(2));
				nvo.setTitle(rs.getString(3));
				nvo.setContents(rs.getString(4));
				nvo.setThumbnail(rs.getString(5));
				nvo.setCreated_at(rs.getString(6));

				noticeList.add(nvo);

			} // end of while(rs.next()) {} -------

		} finally {
			close();
		}

		return noticeList;
	}

	@Override
	public int totalPage(Map<String, String> paraMap) throws SQLException {
		int result = 0;

		try {
			conn = ds.getConnection();
			String sql = " select ceil(count(*)/?) from notice";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("sizePerPage"));
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} finally {
			close();
		}

		return result;
	}

	@Override
	public int delete_notice(String deleteId) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			String sql = "delete from notice where notice_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deleteId);
			result = pstmt.executeUpdate();
			
			
		}finally {
			close();
		}
		
		return result;
	}

	@Override
	public int insertNotice(Map<String, String> paramap) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			String sql = "insert into notice(user_id, title, contents, thumbnail) values(3, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramap.get("title"));
			pstmt.setString(2, paramap.get("contents"));
			pstmt.setString(3, paramap.get("thumbnail"));
			result = pstmt.executeUpdate();
			
			
		}
		finally {
			close();
		}
		
		return result;
	}

	@Override
	public int updateNotice(Map<String, String> paraMap) throws SQLException {

		int result = 0;
		
		try {
			conn = ds.getConnection();
			String sql = "update notice set title = ?,contents = ? where notice_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("title"));
			pstmt.setString(2, paraMap.get("contents"));
			pstmt.setString(3, paraMap.get("notice_id"));
			result = pstmt.executeUpdate();
			
			
		}finally {
			close();
		}
		
		return result;
	}

}
