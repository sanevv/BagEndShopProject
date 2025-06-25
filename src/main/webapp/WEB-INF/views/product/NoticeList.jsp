<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
div#pageBar {
      border: solid 0px red;
      width: 80%;
      margin: 3% auto 0 auto;
      display: flex;
   }
   
div#pageBar > nav {
	margin: auto;
}

</style>

<jsp:include page="../include/header.jsp" />

<div class="container"">
	<div></div>
	<div class="text-center"
		style="font-size: 20pt; font-weight: bold; margin: 50px 0;">NOTICE
		<span style="float: right;">
		<a href="/notice/write" class="btn btn-common" style="border: solid 1px black;">ㅈㅅㅎㄱ</a>
		</span>
	</div>

	<div class="row">
		<fmt:parseNumber var="currentShowPageNo" value="${currentShowPageNo}"></fmt:parseNumber>
		<c:forEach var="notice" items="${noticeList}">
			<div class="col-md-3 mb-3">
				<div class="card shadow-sm h-100">
					<div style="position: relative;">
						<img src="${notice.thumbnail}" class="card-img-top"
							style="width: 100%; height: auto;" />
					</div>

					<div class="card-body" style="font-size: 0.9rem;">
						<p class="mb-3" style="color: #7ff000; margin: 20px 0;">NOTICE</p>
						<h6 class="card-title font-weight-bold mb-1">${notice.title}</h6>
						<p class="card-text text-muted" style="margin: 20px 0;">${notice.contents}</p>
						<div class="text-center mt-3">

							<a href="/notice/detail.one?notice_id=${notice.notice_id}"
								class='btn btn-sm btn-outline-dark stretched-link'></a>

						</div>
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

<jsp:include page="../include/footer.jsp" />