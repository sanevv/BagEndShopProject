<%--
  Created by IntelliJ IDEA.
  User: jks93
  Date: 25. 6. 23.
  Time: 오후 4:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="">

<jsp:include page="../include/header.jsp"/>

<script type="text/javascript">

    $(document).ready(function() {  // DOM 로드 완료 후 실행
        // console.log(window.location.pathname);


        $("button.btn-check").on("click", function(e) {

/*
            console.log($("input[name='check']:checked").val());
            console.log($("#searchType").val());
            console.log($("#name").val());
            console.log($("#email").val());
            console.log($("#phoneNum").val());
 */

            $.ajax({
                url: "/api/test/find",
                type: "POST",
                data: {
                    check: $("input[name='check']:checked").val(),
                    name: $("#name").val(),
                    phoneNum: $("#phoneNum").val()
                },
                success: function(data) {

                    console.log(data);
                    console.log(data.userName);
                    console.log(data.phoneNumber);
                    console.log(data.existUser);
                    if(data.existUser)
                        window.location.href = `/test/findEmailSuccess.up?name=${data.userName}&phoneNum=${data.phoneNumber}`;
                    else
                        window.location.href = `/test/findEmail.up`
                },
                error: function(xhr, status, error) {
                    alert("오류가 발생했습니다: " + error);
                }
            });
        });
    });





</script>

<style>
    body {
        background-color: #fff;
    }

    .custom-form-box {
        width: 100%;
        max-width: 500px;
        margin: 80px auto;
        padding: 40px;
        border-radius: 16px;
        background: #fff;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    }

    .form-control {
        border-radius: 8px;
        height: 48px;
        font-size: 15px;
    }

    .form-label {
        font-weight: 500;
        margin-bottom: 8px;
    }

    .form-check-label {
        margin-right: 20px;
        font-weight: 400;
    }

    .btn-check {
        width: 100%;
        height: 50px;
        background-color: #000;
        color: white;
        border-radius: 8px;
        font-weight: bold;
        border: none;
    }

    .btn-check:hover {
        background-color: #222;
    }
</style>

<div class="container">
    <div class="custom-form-box">
        <form id="findEmailForm">
            <div class="mb-3">
                <label class="form-label d-block">본인확인 인증</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="check" id="phoneCheck" value="phone">
                    <label class="form-check-label" for="phoneCheck">휴대폰번호</label>
                </div>
            </div>

            <div class="mb-3">
                <label for="name" class="form-label">이름</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="이름을 입력하세요.">
            </div>

            <div class="mb-3" id="phoneInput">
                <label for="phoneNum" class="form-label">휴대폰번호로 찾기</label>
                <input type="text" class="form-control" id="phoneNum" name="phoneNum" placeholder="휴대폰번호를 입력하세요.">
            </div>

            <button type="button" class="btn btn-check">확인</button>
        </form>
    </div>
</div>





<jsp:include page="../include/footer.jsp"/>
