package com.github.semiprojectshop.repository.aery.user.domain;


public class MemberVO {

	private int userId;                // 회원아이디
	private String email;              // 이메일
	private String password;           // 비밀번호 (SHA-256 암호화 대상)
	private String name;               // 회원명
	private String phoneNumber ;       // 연락처
	private String zipCode ;              // 우편번호
	private String address;            // 주소
	private String detailAddress;      // 상세주소
	private String registerAt ;        // 가입일자 
	private int roleId = 2;            // roleId 기본값 2(일반사용자)

	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
	    this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getRegisterAt() {
		return registerAt;
	}

	public void setRegisterAt(String registerAt) {
		this.registerAt = registerAt;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
