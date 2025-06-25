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

        const userid = $('#userId').val().trim();
        const username = $('#userName').val();
        const email = $('#email').val();
        const phoneNumber = $('#phoneNumber').val();

        $('button:button').on('click', function(){

            $.ajax({
                //url: /api/findPasswordByEmail/,
                type: 'POST',
                data: {
                    userid: userid,
                    username: username,
                    email: email
                },
                success: function(data) {
                    if (data.existUser) {
                        alert("해당 정보로 가입된 회원이 있습니다.");
                        // 이메일 인증 페이지로 이동
                        window.location.href = "/test/findPasswordEmailCertification.up?userid=" + userid;
                    } else {
                        alert("해당 정보로 가입된 회원이 없습니다.");
                    }
                },

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
