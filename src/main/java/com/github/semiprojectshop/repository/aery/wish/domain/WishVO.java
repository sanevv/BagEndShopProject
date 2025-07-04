package com.github.semiprojectshop.repository.aery.wish.domain;

public class WishVO {

	private int userId;        // 회원아이디
	private int productId;     // 상품아이디
	private String createdAt;  // 위시등록일자
	  
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
}
