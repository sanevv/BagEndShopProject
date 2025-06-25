package com.github.semiprojectshop.repository.seungho.domain;

public class NoticeVO {
	private String notice_id;
	private String userid;
	private String title;
	private String contents;
	private String thumbnail;
	private String created_at;
	
	
	public String getNotice_id() {
		return notice_id;
	}
	
	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
}
