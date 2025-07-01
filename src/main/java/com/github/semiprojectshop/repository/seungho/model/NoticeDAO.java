package com.github.semiprojectshop.repository.seungho.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;

public interface NoticeDAO {
	// 공지사항 인포가져오기
	NoticeVO getNoticeInfo(String notice_id) throws SQLException;

	// 공지사항 리스트 가져오기
	List<NoticeVO> noticeList(Map<String, String> paraMap) throws SQLException;
	// 페이징 처리를 위한 페이지 수 구하기
	int totalPage(Map<String, String> paraMap) throws SQLException;

	int delete_notice(String deleteId) throws SQLException;

	int insertNotice(Map<String, String> paramap) throws SQLException;

	int updateNotice(Map<String, String> paraMap) throws SQLException;

	List<NoticeVO> selectMainPageNotice() throws SQLException;

}
