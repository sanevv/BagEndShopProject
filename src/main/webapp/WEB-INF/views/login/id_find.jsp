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

<script type="text/javascript">
    validInputValue = (emailInput, nameInput) => {
        const email = emailInput.val();
        const name = nameInput.val();

        if (email.length <= 0) {
            alert("이메일을 입력하세요.");
            emailInput.focus();
            return false;
        }
        if (name.length <= 0) {
            alert("이름을 입력하세요.");
            nameInput.focus();
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

    goFind = (email, name) => {
        $.ajax({
            url: '/api/member/find-id',
            type: 'GET',
            data: {
                email: email,
                name: name
            },
            success: function (response) {
                console.log(response);

                if (response.success) {
                    $('#result').show();
                    $('#resultSpan').text(response.userId);
                } else {
                    // #result의 내용에서 'ID :'만 제거
                    $('#result').show();
                    $('#resultSpan').text("입력하신 정보와 일치하는 아이디가 없습니다.");
                }
            },
            error: function () {
                alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
            }
        });
    }


    $(function () {
        const emailInput = $("input[name='email']");
        const nameInput = $("input[name='name']");
        $('button.btn-success').click(function () {
            if (!validInputValue(emailInput, nameInput)) {
                return;
            }
            goFind(emailInput.val(), nameInput.val());
        });
        emailInput.on('keyup', (e) => {
            if (e.keyCode!==13 || !validInputValue(emailInput, nameInput)) {
                return;
            }
            goFind(emailInput.val(), nameInput.val());
        })
        nameInput.on('keyup', (e) => {
            if (e.keyCode!==13 || !validInputValue(emailInput, nameInput)) {
                return;
            }
            goFind(emailInput.val(), nameInput.val());
        })


    });// 페이지 로딩 후 실행할 코드끗

    // 모달 닫기 버튼 클릭 시
    idFindCloseEvent = () =>{
        //.empty() 는 자식 요소를 모두 제거 안에 내용물 전부 제거
        $('#result').hide();
        $('#resultSpan').text('');
        $('input[name="email"]').val('');
        $('input[name="name"]').val('');
    }

</script>

<form name="idFindFrm">
    <ul style="list-style-type: none;">
        <li style="margin: 25px 0">
            <label style="display: inline-block; width: 90px;">성명</label>
            <input type="text" name="name" size="25" autocomplete="off"/>
        </li>
        <li style="margin: 25px 0">
            <label style="display: inline-block; width: 90px;">이메일</label>
            <input type="text" name="email" size="25" autocomplete="off"/>
        </li>
    </ul>

    <div class="my-3 text-center">
        <button type="button" class="btn btn-success">찾기</button>
    </div>
</form>

<div id="result" style="display: none" class="my-3 text-center">
    ID : <span id="resultSpan" style="color: red; font-size: 16pt; font-weight: bold;"></span>
</div>
