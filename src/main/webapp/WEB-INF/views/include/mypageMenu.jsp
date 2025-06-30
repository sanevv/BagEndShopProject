<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/mypageMenu.css">

<div class="container mt-5">
    <div class="row">
		<div class="sidebar col-lg-3 col-md-4 pt-4">

		    <!-- 회원 간단 정보 -->
		    <div class="userinfo">
		        <span class="h4 m-0" style="color:black; font-weight: bold;">${sessionScope.loginUser.name} 님</span>
		        <button type="button" class="btn btn-dark btn-sm" id="contact" style="background-color: black;">1:1 문의하기</button>
		    </div>
		
		    <!-- 마이페이지 메뉴 -->
		    <aside class="side-menu">        
		    
		    	<!-- 쇼핑 정보 -->
		        <div class="menu-list">
		            <h5 class="title">쇼핑 정보</h5><ul class="list">
		                <li><a href="#">주문 내역</a></li>
		                <li><a href="/wishList.team1">위시리스트</a></li>
		            </ul>
		        </div>
		        
		        <!-- 1:1 문의 -->
		        <div class="menu-list">
		            <h5 class="title">1:1 문의</h5>
		            <ul class="list">
		                <li><a href="#">1:1 문의하기</a></li>
		                <li><a href="#">1:1 문의내역</a></li>
		            </ul>
		        </div>
		        
		        <!-- 회원 정보 -->
		        <div class="menu-list">
		            <h5 class="title">회원 정보</h5>
		            <ul class="list">
		                <li><a href="/member/modify.html">회원 정보</a></li>
		            </ul>
		        </div>
		    </aside>
		</div>
	</div>
</div>		