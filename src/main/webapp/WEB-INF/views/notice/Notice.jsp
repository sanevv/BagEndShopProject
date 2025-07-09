<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<!-- Bootstrap & Bootstrap Icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<style>
#notice_head {
	max-width: 800px;
	margin: 0 auto;
	position: relative;
	padding: 150px 0 60px ;
	text-align: center;
}

.dropdown.custom-dropdown {
	position: absolute;
	top: 100px;
	right: 0;
	z-index: 1000;
}

.dropdown-menu .dropdown-item {
	font-size: 0.9rem;
	padding: 5px 12px;
}

.bi-gear-fill {
	font-size: 1.3rem;
	color: #333;
}
</style>

<script type="text/javascript">

$(function(){
	$('button#dropdownMenuButton').hide();
	
	const roleId = $('input:hidden[name="roleId"]').val();
	
	if(roleId == 1) {
		$('button#dropdownMenuButton').show();
	}
	
	
});
// Function Declartion
function goEdit(event) {
	event.preventDefault();
    if (confirm("수정할꺼야?")) {

        const frm = document.editFrm;
        frm.action = "${pageContext.request.contextPath}/notice/edit";
        frm.method = "post";
        frm.submit();
        
        
    }
}

function goDelete(noticeId) {
    if (confirm("삭제해?")) {
        const noticeID = $(noticeId).data("id");

        $.ajax({
            url: "${pageContext.request.contextPath}/notice/delete",
            type: "post",
            data: { noticeID: noticeID },
            success: function(response) {
                alert("삭제 완료!");
                location.href = "${pageContext.request.contextPath}/notice/list.one";
            },
            error: function(request, status, error) {
                alert("code: " + request.status + "\n" +
                      "message: " + request.responseText + "\n" +
                      "error: " + error);
            }
        });
    }
}
</script>

<jsp:include page="../include/header.jsp" />

<div class="text-center" id="notice_head">

<c:if test="${not empty loginUser && loginUser.roleId == 1}">
	<div class="dropdown custom-dropdown">
		<button class="btn btn-light btn-sm dropdown-toggle border-0"
			type="button" id="dropdownMenuButton" data-bs-toggle="dropdown"
			aria-expanded="false" title="관리 메뉴">
			<i class="bi bi-gear-fill"></i>
		</button>
		<ul class="dropdown-menu dropdown-menu-end"
			aria-labelledby="dropdownMenuButton">
			<li><a class="dropdown-item" href="#" onclick="goEdit(event);">수정하기</a></li>
			<li><a class="dropdown-item text-danger" href="#"
				data-id="${nvo.notice_id}" onclick="goDelete(this)">삭제하기</a></li>
		</ul>
		
			<form name="editFrm" style="display:none;">
		<input type="hidden" name="noticeID" value="${nvo.notice_id}">
	</form>
	</c:if>
	
		<!-- 목록으로 버튼 -->
	<a href="${pageContext.request.contextPath}/notice/list.one"
	   style="font-size: 12pt; position: absolute; top: 200px; right: 0px;">
	   목록으로
	</a>
	</div>
	

	
	<!-- NOTICE 제목 -->
	<div class="notice_title" style="text-align: center;">
	<span
		style="color: #7ff000; font-size: 20px; font-weight: bold; line-height: 100px;">NOTICE</span>
	

	<h2 class="title" style="font-weight: bold; font-size: 20pt;">${nvo.title}</h2>

	<p id="date"
		style="font-size: 10pt; margin: 30px 0 100px 0; color: rgba(0, 0, 0, 0.5);">
		작성일 <span style="color: rgba(0, 0, 0, 0.7);">${nvo.created_at}</span>
	</p>
	</div>
	<img style="height: 500px; margin: 0 auto;" src="${nvo.thumbnail}">

	<div class="text-center" style="margin-top: 50px; font-size: 13pt;" id="contents">${nvo.contents}</div>
</div>
<input name="roleId" type="hidden" value="${loginUser.roleId}">
<jsp:include page="../include/footer.jsp" />