<%@page
	import="com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../include/header.jsp" />
<style type="text/css">
div#pageBar {
	border: solid 0px red;
	width: 80%;
	margin: 3% auto 0 auto;
	display: flex;
}

div#pageBar>nav {
	margin: auto;
}

div.notice-card > img {
  width: 100%;         
  height: 250px;        
  object-fit: cover;    
 
}

</style>

<script>
$(function(){
	$('input:button[name="write"]').hide();
	
	const roleId = $('input:hidden[name="roleId"]').val();
	
	if(roleId == 1) {
		$('input:button[name="write"]').show();
	}

});

	function goWrite(){
	const frm = document.noticeFrm;
	frm.method = "post"
	frm.action = "${pageContext.request.contextPath}/notice/write"
	frm.submit();
	}
</script>

<main id="main">

<div class="container">
	<div></div>
	<div class="text-center"
		style="font-size: 20pt; font-weight: bold; margin: 50px 0;">
		NOTICE

		<form name="noticeFrm">
			<input name="write" class="btn btn-common"
				style="float: right; font-size: 12pt;" type="button" value="공지작성"
				onclick="goWrite();">
		</form>
	</div>

	<div class="row">
		<fmt:parseNumber var="currentShowPageNo" value="${currentShowPageNo}"></fmt:parseNumber>
		<c:forEach var="notice" items="${noticeList}">
			<div class="col-md-3 mb-3">
				<div class="card shadow-sm h-100 position-relative">
					<div class="notice-card" style="position: relative;">
						<img src="${notice.thumbnail}" class="card-img-top"/>
					</div>

					<div class="card-body" style="font-size: 0.9rem;">
						<p class="mb-3" style="color: #7ff000; margin: 10px 0 20px 0;">NOTICE</p>
						<h6 class="card-title font-weight-bold mb-1">${notice.title}</h6>
						<p class="card-text text-muted" style="margin: 10px 0;">${notice.contents}</p>

						<a href="/notice/detail.one?notice_id=${notice.notice_id}"
							class="stretched-link"></a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>

<div id="pageBar">
	<nav style="text-align: center;">
		<ul class="pagination mt-5">${pageBar}</ul>
	</nav>
</div>
<input name="roleId" type="hidden" value="${loginuser.roleId}">
</main>
<jsp:include page="../include/footer.jsp" />