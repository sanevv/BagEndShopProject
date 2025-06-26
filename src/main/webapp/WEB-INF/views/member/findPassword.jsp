<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../include/header.jsp"/>

<style>
    body {
        background-color: #fff;
        margin: 0;
        padding: 0;
    }

    .form-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: calc(100vh - 160px); /* 헤더+푸터 높이 예상 값 */
        padding: 40px 20px;
        box-sizing: border-box;
    }

    .form-container {
        width: 100%;
        max-width: 400px;
    }

    .btn-black {
        background-color: #000;
        color: #fff;
    }

    .form-check-label {
        margin-right: 20px;
    }

    .d-block{
        margin-bottom: 15px;
    }
</style>

<script type="text/javascript">

    $(function (){

        // 초기 상태 설정
        $('#email').show();
        $('#emailText').show();
        $('#phoneNumber').hide();
        $('#phoneNumberText').hide();

        // 인증방법 선택에 따라 입력 필드 변경
        $('input[name="authMethod"]').change(function() {
            if ($(this).val() === 'email') {
                $('#email').show();
                $('#emailText').show();
                $('#phoneNumber').hide();
                $('#phoneNumberText').hide();
            } else {
                $('#email').hide();
                $('#emailText').hide();
                $('#phoneNumber').show();
                $('#phoneNumberText').show();
            }
        });



        $('button:button').on('click', function(){

            const userid = $('#userId').val().trim();
            const username = $('#userName').val();
            const email = $('#email').val();
            const phoneNumber = $('#phoneNumber').val();

            $.ajax({
                url: "/api/find/email",
                type: 'GET',
                data: {
                    userid: userid,
                    username: username,
                    email: email
                },
                success: function(data) {
                    console.log("data", data);
                    console.log("들어오냐", data.existEmail)
                    console.log("이메일", data.email);
                    console.log("아이디", data.userid);
                    console.log("이름", data.username);
                    if (data.existEmail) {
                        window.location.href = "/test/receiveAuthenticationNumberByEmail?email="+data.email;
                    } else {
                        alert("해당 정보로 가입된 회원이 없습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("오류 발생:", error);
                    alert("오류가 발생했습니다: " + error);
                }

            })

            $.ajax({
                url: "/api/find/phone",
                type: 'GET',
                data: {
                    userid: userid,
                    username: username,
                    phoneNumber: phoneNumber
                },
                success: function(data) {
                    console.log("data", data);
                    console.log("들어오냐번호", data.existPhoneNum)
                    console.log("전화번호", data.phoneNumber);
                    console.log("아이디", data.userid);
                    console.log("이름", data.username);
                    if (data.existPhoneNum) {
                        window.location.href = "/test/receiveAuthenticationNumberByPhonenum?phoneNumber="+data.phoneNumber;
                    } else {
                        alert("해당 정보로 가입된 회원이 없습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("오류 발생:", error);
                    alert("오류가 발생했습니다: " + error);
                }

            })

        })



    })

</script>

<!-- ✅ 본문을 감싸는 wrapper 추가 -->
<div class="form-wrapper">
    <div class="form-container">
        <form method="post" action="yourProcess.jsp">
            <!-- 인증방법 -->
            <div class="mb-3">
                <label class="form-label d-block">인증방법</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="authMethod" id="emailAuth" value="email" checked>
                    <label class="form-check-label" for="emailAuth">이메일</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="authMethod" id="phoneAuth" value="phone">
                    <label class="form-check-label" for="phoneAuth">휴대폰번호</label>
                </div>
            </div>

            <!-- 아이디 -->
            <div class="mb-3">
                <label for="userId" class="form-label mb-2">아이디</label>
                <input type="text" class="form-control" id="userId" name="userId">
            </div>

            <!-- 이름 -->
            <div class="mb-3">
                <label for="userName" class="form-label mb-2">이름</label>
                <input type="text" class="form-control" id="userName" name="userName">
            </div>

            <!-- 이메일 -->
            <div class="mb-3">
                <label for="email" id="emailText" class="form-label mb-2">이메일로 찾기</label>
                <input type="email" class="form-control" id="email" name="email">
            </div>
            <!-- 휴대폰번호 -->
            <div class="mb-3">
                <label for="phoneNumber" id="phoneNumberText" class="form-label mb-2">휴대폰번호로 찾기</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
            </div>
            <!-- 확인 버튼 -->
            <button type="button" class="btn btn-black w-100">확인</button>
        </form>
    </div>
</div>

<jsp:include page="../include/footer.jsp"/>
