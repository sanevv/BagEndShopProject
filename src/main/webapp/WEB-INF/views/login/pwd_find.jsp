<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 10.
  Time: 오후 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="utf-8">
<meta name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Optional JavaScript -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<%-- jQueryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css"/>
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>
<%--axios 라이브러리--%>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script type="text/javascript">
    validInputValue = (emailInput, userIdInput) => {
        const email = emailInput.val();
        const name = userIdInput.val();

        if (email.length <= 0) {
            alert("이메일을 입력하세요.");
            emailInput.focus();
            return false;
        }
        if (name.length <= 0) {
            alert("아이디를 입력하세요.");
            userIdInput.focus();
            return false;
        }
        // 이메일 형식 검사
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test(email)) {
            alert("이메일 형식이 올바르지 않습니다.");
            emailInput.focus();
            return false;
        }
        return true;
    }

    async function sendToVerifyEmail(email) {
        const response = await fetch('/api/mail/send?email=' + encodeURIComponent(email), {
            method: 'POST'
        });
        if (!response.ok) throw new Error('메일 전송 실패');
        return await response.json();
    }


    goFind = (email, userId) => {
        $.ajax({//먼저 사용자의 존재 여부를 확인한다.
            url: '/api/member/find-pwd',
            type: 'GET',
            data: {
                email: email,
                userId: userId
            },
            success: async function (response) {
                if (response.success) {
                    try {// 인증 코드 전송
                        sendToVerifyEmail(email);
                    } catch (e) {
                        $('#searchBtn').show(); // 버튼 다시 보이게
                        $('#successDiv').hide();
                        $('#result').hide(); // 부모도 숨김
                        $('input[name="email"]').prop('disabled', false);
                        $('input[name="userId"]').prop('disabled', false);
                        alert("인증 코드 전송에 실패했습니다. 나중에 다시 시도해주세요.");
                        return;
                    }
                    $('#searchBtn').hide(); // 버튼 숨김
                    $('#result').show(); // 추가: 부모도 보이게
                    $('#successDiv').show();
                    $('#successSpan').html(`인증 코드가 ${email}로 전송되었습니다.<br>인증코드를 입력하세요.`);
                    $('#failSpan').hide(); // 실패 메시지는 숨김
                    //이메일 입력필드 아이디 입력필드 비활성화
                    $('input[name="email"]').prop('disabled', true);
                    $('input[name="userId"]').prop('disabled', true);
                } else {
                    $('#result').show(); // 추가: 부모도 보이게
                    $('#failSpan').show();
                    $('#successDiv').hide(); // 성공 메시지는 숨김
                }
            },
            error: function () {
                alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
            }
        });
    }


    $(function () {// 페이지 로딩 후 실행할 코드

        const emailInput = $("input[name='email']");
        const userIdInput = $("input[name='userId']");
        $('#searchBtn').click(function () {
            if (!validInputValue(emailInput, userIdInput)) {
                return;
            }
            goFind(emailInput.val(), userIdInput.val());
        });
        emailInput.on('keyup', (e) => {
            if (e.keyCode !== 13 || !validInputValue(emailInput, userIdInput)) {
                return;
            }
            goFind(emailInput.val(), userIdInput.val());
        })
        userIdInput.on('keyup', (e) => {
            if (e.keyCode !== 13 || !validInputValue(emailInput, userIdInput)) {
                return;
            }
            goFind(emailInput.val(), userIdInput.val());
        })
        const verifyInput = $('#verifyCodeInput');
        const verifyBtn = $('#verifyBtn').on('click', function () {
            if(verifyInput.val().length <= 0) {
                alert('인증코드를 입력하세요.');
                verifyInput.focus();
                return;
            }
            verifyRequestEvent(emailInput.val(), verifyInput.val());
        });
        $('#recurrence').on('click', function () {
            // 인증 코드 재발송
            sendToVerifyEmail(emailInput.val()).then(r => console.log(r));
            alert('인증코드가 재발송되었습니다. 10분 이내에 인증해주세요.');
        });

        const newPwdConfirm = $('#newPwdConfirm')
        const newPwd = $('#newPwd')
        const changePwdBtn = $('#changePwdBtn').click(() => {
            const newPwdVal = newPwd.val();
            const newPwdConfirmVal = newPwdConfirm.val();
            if (pwValid(newPwdVal, newPwdConfirmVal))
                return;

            // 비밀번호 변경 요청
            requestPasswordChange(userIdInput.val(), newPwdVal, newPwdConfirmVal);
        })
        verifyInput.on('keyup', (e) => {
            if (e.keyCode !== 13) return;
            verifyBtn.click();
        });
        $('#newPwd, #newPwdConfirm').on('keyup', (e) => {
            if (e.keyCode !== 13) return;
            changePwdBtn.click();
        });


    });// 페이지 로딩 후 실행할 코드끗
    //비밀번호 변경 api 호출
    requestPasswordChange = async (userId, newPwd, newPwdConfirm) => {
        await axios.post('/api/member/update-password', {
            userId: userId,
            password: newPwd,
            passwordConfirm: newPwdConfirm
        })
            .then(response => {
                console.log(response)
                alert('비밀번호가 성공적으로 변경되었습니다.');
                // 비밀번호 변경 성공 시 부모의 닫기 버튼 클릭 이벤트 발생
                parent.$('.passwdFindClose').click();
            })
            .catch(error => {
                alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요.');
            })
    }


    verifyRequestEvent = async (email, code) => {
        // 인증 요청
        try {
            const response = await axios.get('/api/mail/verify', {
                params: {
                    email: email,
                    code: code
                }
            });
            console.log(response);
            if (response.data) {
                alert('인증 성공 하였습니다. 계정의 비밀번호를 변경해주세요.');
                $('#result').hide();
                $('#pwdChangeDiv').css('display', 'flex');
                $('[name="pwdFindFrm"]').hide(); // 인증 후 폼 숨김
            } else {
                alert('코드 유효기간이 지났거나 인증코드가 일치하지 않습니다. 10분이내에 인증 해주세요.');
                return;
            }
        } catch (e) {
            alert('서버 오류 발생했습니다. 나중에 다시 시도해주세요.');
        }
    }

    //새로운 비밀번호 검증
    pwValid = (newPwd, confirm) => {
        const pwdPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
        if (!pwdPattern.test(newPwd)) {
            alert('비밀번호는 영문, 숫자, 특수문자를 포함하여 8자리 이상이어야 합니다.');
            newPwd.focus();
            return true;
        }
        if (newPwd !== confirm) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            newPwd.focus();
            return true;
        }
    }

    // 모달 닫기 버튼 클릭 시
    pwdFindCloseEvent = () => {
        //.empty() 는 자식 요소를 모두 제거 안에 내용물 전부 제거
        $('#result').hide();
        $('input[name="email"]').val('').prop("disabled", false);
        $('input[name="userId"]').val('').prop("disabled", false);
        $('#pwdChangeDiv').hide();
        $('#searchBtn').show();
        $('[name="pwdFindFrm"]').show(); // 닫기후
        $('#verifyCodeInput').val(''); // 인증 코드 입력 필드 초기화
        $('#newPwd, #newPwdConfirm').val(''); // 비밀번호 입력 필드 초기화
    }

</script>

<form name="pwdFindFrm">
    <ul style="list-style-type: none;">
        <li style="margin: 25px 0">
            <label style="display: inline-block; width: 90px;">아이디</label>
            <input type="text" name="userId" size="25" autocomplete="off"/>
        </li>
        <li style="margin: 25px 0">
            <label style="display: inline-block; width: 90px;">이메일</label>
            <input type="text" name="email" size="25" autocomplete="off"/>
        </li>
    </ul>

    <div class="my-3 text-center">
        <button id="searchBtn" type="button" class="btn btn-success">찾기</button>
    </div>
</form>

<div id="result" style="display: none" class="my-3 text-center">
    <span id="failSpan" style="color: red; font-weight: bold; display: none">사용자 정보가 없습니다.</span>
    <div style="display: none" id="successDiv">
        <span id="successSpan" style="font-size: 10pt;"></span>
        <br>
        <label for="verifyCodeInput">인증코드 : </label>
        <input type="text" id="verifyCodeInput"/>
        <br><br>
        <button id="recurrence" type="button" class="btn btn-success">코드재발송</button>
        <button id="verifyBtn" type="button" class="btn btn-info">인증하기</button>
    </div>
</div>
<%--변경할 비밀번호와 비밀번호 확인 인풋 태그--%>
<div id="pwdChangeDiv" class="flex-column justify-content-evenly align-items-center"
     style="height: 100vh; display: none">
    <label for="newPwd">⬇️ 변경할 비밀번호 ⬇️</label>
    <input type="password" id="newPwd" name="newPwd"/>
    <br>
    <label for="newPwdConfirm">⬇️ 비밀번호 확인 ⬇️</label>
    <input type="password" id="newPwdConfirm" name="newPwdConfirm"/>
    <br>
    <button id="changePwdBtn" type="button" class="btn btn-success">비밀번호 변경</button>
</div>
