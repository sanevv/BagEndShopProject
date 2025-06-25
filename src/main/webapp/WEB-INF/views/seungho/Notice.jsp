<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../include/header.jsp" />



<div id="container">

	<div class="text-center" id="notice_head">
		<span style="color: #7ff000; line-height: 100px;">NOTICE</span>

		<h2 class="title" style="font-weight: bold; font-size: 20pt;">${nvo.title}</h2>


		<p id="date"
			style="font-size: 10pt; margin: 30px 0 100px 0; color: rgba(0, 0, 0, 0.5);">
			작성일 <span style="color: rgba(0, 0, 0, 0.7);">${nvo.created_at}</span>
		</p>

		<div class="text-center" id="contents">${nvo.contents}</div>

		
	</div>


</div>


<jsp:include page="../include/footer.jsp" />
