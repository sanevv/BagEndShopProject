<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"></jsp:include>
<style type="text/css">
    body {
        margin: 0;
        padding: 0;
        font-family: 'Segoe UI', sans-serif;
        background-color: #f9f9f9;
    }

    div#container {
        width: 80%;
        margin: 0 auto;
        padding-top: 80px; 
    }

    h1 {
        margin-top: 150px;
        font-size: 40px;
        font-weight: bold;
        text-align: center;
    }

    form[name="writeFrm"] {
        margin: 50px auto 0 auto;
        width: 60%;
        background-color: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        display: flex;
        flex-direction: column;
        gap: 20px;
    }

    input#title, textarea#contents {
        width: 100%;
        padding: 12px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    textarea#contents {
        height: 250px;
        resize: vertical;
    }

    .form-buttons {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 10px;
    }

    .btn {
        padding: 10px 20px;
        border-radius: 5px;
        font-size: 14px;
        text-decoration: none;
        cursor: pointer;
    }

    .btn-success {
        background-color: #4CAF50;
        color: white;
        border: none;
    }

    .btn-common {
        background-color: white;
        color: #333;
        border: 1px solid #999;
    }

    .btn-common:hover {
        background-color: #f0f0f0;
    }
</style>

<script type="text/javascript">
$(function() {
	  $('button.btn-success').click(function(e) {
	    e.preventDefault();

	    const form = document.forms["writeFrm"];
	    const formData = new FormData(form); 

	    $.ajax({
	      url: "${pageContext.request.contextPath}/notice/abc",
	      type: "post",
	      data: formData,
	      processData: false,     // 반드시 false
	      contentType: false,     // 반드시 false
	      dataType: "json",       
	      success: function(json) {
	        console.log("응답:", JSON.stringify(json));
	        if (json.result == 1) {
	          location.href = json.url;
	        }
	      },
	      error: function(request, status, error) {
	        alert("에러 발생!");
	        console.log("code:", request.status);
	        console.log("message:", request.responseText);
	        console.log("error:", error);
	      }
	    });
	  });
	});

</script>



<div id="container">
    <h1>공지사항 쓰기</h1>

    <form name="writeFrm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/notice/abc" >
    	<input name="thumbnail" type="file">
        <input name="title" id="title" type="text" placeholder="제목을 입력하세요">
        <textarea name="contents" id="contents" placeholder="내용을 입력하세요"></textarea>

        <div class="form-buttons">
            <button class="btn btn-success" type="button" >글쓰기</button>
            <a class="btn btn-common" style="border: solid 1px black" href="${pageContext.request.contextPath}/notice/list.one">취소</a>
        </div>
    </form>
</div>

<jsp:include page="../include/footer.jsp"></jsp:include>