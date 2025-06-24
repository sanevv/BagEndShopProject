package com.github.semiprojectshop.repository.seungho.model;

import java.sql.SQLException;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;

public interface NoticeDAO {
	// 공지사항 인포가져오기
	NoticeVO getNoticeInfo(String notice_id) throws SQLException;

}
