package com.github.semiprojectshop.repository.seungho.model;

import java.sql.SQLException;

import com.github.semiprojectshop.repository.seungho.domail.NoticeVO;

public interface NoticeDAO {
	// 공지사항 인포가져오기
	NoticeVO getNoticeID(String notice_id) throws SQLException;

}
