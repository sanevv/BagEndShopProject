<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>




<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/mypageMenu.css">

<script type="text/javascript">

	function Logout() {
		if (confirm("로그아웃 하시겠습니까?")) {
			location.href = "${pageContext.request.contextPath}/test/logout";
		}
	}

</script>



<div class="container mt-5" style="width: 900px; ">
    <div class="row">
		<div class="sidebar col-lg-3 col-md-4 pt-4">

		    <!-- 회원 간단 정보 -->
		    <div class="userinfo">
		        <span class="h4 m-0 mr-1" style="color:black; font-weight: bold;">${sessionScope.loginUser.name} 님</span>
		        <button type="button" class="btn btn-dark btn-sm" id="contact" style="background-color: black;">1:1 문의하기</button>
		    </div>
		
		    <!-- 마이페이지 메뉴 -->
		    <aside class="side-menu">        
		    
		    	<!-- 쇼핑 정보 -->
		        <div class="menu-list">
		            <h5 class="title">쇼핑 정보</h5><ul class="list"><br>
		                <li><a href="${pageContext.request.contextPath}/cart">주문 내역</a></li><br>
		                <li><a href="/wishList.team1">위시리스트</a></li><br>
		            </ul>
		        </div>
		        
		        <!-- 1:1 문의 -->
		        <div class="menu-list">
		            <h5 class="title">1:1 문의</h5><br>
		            <ul class="list">
		                <li><a href="#">1:1 문의하기</a></li><br>
		                <li><a href="#">1:1 문의내역</a></li><br>
		            </ul>
		        </div>
		        
		        <!-- 회원 정보 -->
		        <div class="menu-list">
		            <h5 class="title">회원 정보</h5><br>
		            <ul class="list">
		                <li><a href="/test/memberOneChange">회원 정보</a></li>
		            </ul>
		        </div>

				<button class="btn btn-black" type="button" id="logout" name="logout" onclick="Logout()">로그아웃</button>
		    </aside>
		</div>
	</div>
</div>


