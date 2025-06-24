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
        $("button.btn-primary").on("click", function(e) {
            $.ajax({
                url: "/test/findEmail.up",
                type: "POST",
                data: {
                    searchType: $("#searchType").val(),
                    check: $("input[name='check']:checked").val(),
                    name: $("#name").val(),
                    email: $("#email").val(),
                    phoneNum: $("#phoneNum").val()
                },
                success: function(data) {
                    console.log(data);
                },
                error: function(xhr, status, error) {
                    alert("오류가 발생했습니다: " + error);
                }
            });
        });
    });



</script>

<style>
    .form-container {
        max-width: 500px;
        margin: 80px auto;
        padding: 30px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        border-radius: 10px;
        background-color: #fff;
    }
</style>

<div class="container">
    <div class="form-container">
        <form id="findEmailForm">
            <div class="mb-3">
                <label for="searchType" class="form-label">회원유형</label>
                <select class="form-select" id="searchType" name="searchType">
                    <option value="indi">개인회원</option>
                    <option value="indibuis">개인 사업자회원</option>
                    <option value="corp">법인 사업자회원</option>
                    <option value="fore">외국인회원</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label d-block">본인확인 인증</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="check" id="emailCheck" value="email" checked>
                    <label class="form-check-label" for="emailCheck">이메일</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="check" id="phoneCheck" value="phone">
                    <label class="form-check-label" for="phoneCheck">휴대폰번호</label>
                </div>
            </div>

            <div class="mb-3">
                <label for="name" class="form-label">이름</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="이름을 입력하세요.">
            </div>

            <div class="mb-3" id="emailInput">
                <label for="email" class="form-label">이메일로 찾기</label>
                <input type="text" class="form-control" id="email" name="email" placeholder="이메일을 입력하세요.">
            </div>

            <div class="mb-3 d-none" id="phoneInput">
                <label for="phoneNum" class="form-label">휴대폰번호로 찾기</label>
                <input type="text" class="form-control" id="phoneNum" name="phoneNum" placeholder="휴대폰번호를 입력하세요.">
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-dark">확인</button>
            </div>
        </form>
    </div>
</div>

<script>
    const emailInput = document.getElementById('emailInput');
    const phoneInput = document.getElementById('phoneInput');

    document.querySelectorAll('input[name="check"]').forEach((radio) => {
        radio.addEventListener('change', function () {
            if (this.value === 'email') {
                emailInput.classList.remove('d-none');
                phoneInput.classList.add('d-none');
            } else {
                phoneInput.classList.remove('d-none');
                emailInput.classList.add('d-none');
            }
        });
    });
</script>



















<jsp:include page="../include/footer.jsp"/>
