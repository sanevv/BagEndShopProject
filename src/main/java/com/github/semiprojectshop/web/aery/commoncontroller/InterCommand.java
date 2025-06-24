package com.github.semiprojectshop.web.aery.commoncontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterCommand {

	void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	// 웹페이지를 만드는 메소드가 되어짐
	// URL 별로 책임지는 클래스가 있고 클래스에서 강제적으로 execute 라는 메소드를 만들게 된다.
	// 현재 클래스의 execute 는 공통사항을 위해 만든 것이다.
	
}
