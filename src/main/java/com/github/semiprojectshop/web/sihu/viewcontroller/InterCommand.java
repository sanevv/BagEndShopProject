package com.github.semiprojectshop.web.sihu.viewcontroller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterCommand  {

    // 웹페이지에 출력하기 위해선 꼭 2가지 객체가 필요하다.
    void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
