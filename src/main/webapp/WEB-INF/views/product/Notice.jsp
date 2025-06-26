<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">


function goDelete() {
    if (confirm("삭제해?")) {
        const noticeID = $('button#delete').val();

        $.ajax({
            url: "${pageContext.request.contextPath}/notice/list.one", // ❗Controller 경로는 실제 매핑된 URL로 바꾸세요
            type: "post",
            data: { noticeID: noticeID },
            success: function(response) {
                alert("삭제 완료!");
                // location.reload(); 또는 페이지 이동 등 추가 가능
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



<div id="container">

	<div class="text-center" id="notice_head">
		<span style="color: #7ff000; line-height: 100px;">NOTICE</span>

		<h2 class="title" style="font-weight: bold; font-size: 20pt;">${nvo.title}</h2>

		<button id="delete" value="${nvo.notice_id}" onclick="goDelete()">ㅅㅈㅎㄱ</button>
		<p id="date"
			style="font-size: 10pt; margin: 30px 0 100px 0; color: rgba(0, 0, 0, 0.5);">
			작성일 <span style="color: rgba(0, 0, 0, 0.7);">${nvo.created_at}</span>
		</p>

		<div class="text-center" id="contents">${nvo.contents}</div>


	</div>


</div>


<jsp:include page="../include/footer.jsp" />
