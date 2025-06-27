<%--
  Created by IntelliJ IDEA.
  User: jks93
  Date: 25. 6. 26.
  Time: 오후 3:44
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: jks93
  Date: 25. 6. 26.
  Time: 오후 2:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../include/header.jsp"/>

<script>

    $(function () {

        $('#sendCodeBtn').on('click', function () {

            // 사용 예시
            isEmailVerify().done(function (response) {
                if (response) {
                    alert("인증번호가 발송되었습니다.");
                } else {
                    alert("인증번호 발송에 실패했습니다. 다시 시도해주세요.");
                }
            });
        }) // $('#sendCodeBtn').on('click', function (){}
        $('#verificationButton').on('click', function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/api/phone/verify",
                type: "GET",
                data: {
                    phoneNumber: "${requestScope.phoneNumber}",
                    code: $('#verificationCode').val()
                },
                success: function (response) {
                    if(response){
                        window.location.href = "/test/resetPassword?phoneNumber=${requestScope.phoneNumber}";
                    }
                    else{
                        alert("인증번호가 일치하지 않습니다. 다시 시도해주세요.");
                    }
                },
                error: function (xhr, status, error) {
                    alert("오류가 발생했습니다: " + error);
                }

            }) // $.ajax

        })// $('#verificationButton').on('click', function (){}

    }); // $(function)
    function isEmailVerify() {
        return $.ajax({
            url: "${pageContext.request.contextPath}/api/phone/send",
            type: "POST",
            contentType: "application/json", // 중요!!
            data: JSON.stringify({phoneNumber: "${requestScope.phoneNumber}"})
        });
    }
</script>


<div class="container mt-5" style="max-width: 500px;">
    <h3>본인확인 인증</h3><br>
    <p class="text-muted">비밀번호 찾기 본인확인 인증</p><br>

    <form id="verifyForm">
        <div class="mb-3">
            <label class="form-label fw-bold mb-2">본인확인 인증</label><br/>
            <input type="radio" id="phoneNumber" name="authMethod" value="phoneNumber" checked/>
            <label for="phoneNumber">휴대폰</label>
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold mb-2">휴대폰 번호</label>
            <span id="userEmail">${requestScope.phoneNumber}</span>
            <button type="button" class="btn btn-outline-primary btn-sm ms-2 ml-2" id="sendCodeBtn">인증번호 받기</button>
            <span id="countdownText" class="text-danger ms-2"></span>
        </div>

        <div class="mb-3">
            <label for="verificationCode" class="form-label fw-bold mb-2">인증번호</label>
            <input type="text" id="verificationCode" name="verificationCode" class="form-control"/>
        </div>

        <div class="mb-3">
            <small class="text-muted">
                1회 발송된 인증번호의 유효 시간은 <strong>10분</strong>이며,<br><br>
                1회 인증번호 발송 후 <strong>30초 이후</strong>에 재전송이 가능합니다.
            </small>
        </div>

        <div class="d-flex gap-2">
            <button type="button" class="btn btn-primary mr-2" id="verificationButton">확인</button>
            <button type="button" class="btn btn-secondary" onclick="location.href='/'">취소</button>
        </div>
    </form>
</div>


<jsp:include page="../include/footer.jsp"/>

