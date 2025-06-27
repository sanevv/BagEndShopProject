<%--
  Created by IntelliJ IDEA.
  User: jks93
  Date: 25. 6. 27.
  Time: 오후 4:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../include/header.jsp"/>

<script type="text/javascript">

    $(function (){

      $('#error').hide();


      $('#passwordChange').on('click', function () {

        let newPassword = $('#newPassword').val().trim();
        let confirmPassword = $('#confirmPassword').val().trim();

        // 비밀번호 유효성 검사
        const passwordPattern = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
        if (!passwordPattern.test(newPassword)) {
          alert("비밀번호는 영문 대소문자, 숫자, 특수문자 중 2가지 이상 조합으로 8자 이상 15자 이하로 입력해주세요.");
          newPassword = $('#newPassword').val('');
          confirmPassword = $('#confirmPassword').val('');
          return;
        }

        // 비밀번호 일치 여부 확인
        if (newPassword !== confirmPassword) {
          $('#error').show();
          return;
        }

        // 비밀번호 변경 요청
        $.ajax({
          url: '/api/member/resetPassword',
          type: 'PUT',
          data: {
            newPassword: newPassword,
            email: "${requestScope.email}",
            phoneNumber: "${requestScope.phoneNumber}"
          },
          success: function(response) {
            if (response) {
              alert("비밀번호가 성공적으로 변경되었습니다.");
              window.location.href = '/test/login.up';
            } else {
              alert("비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            }
          },
          error: function(xhr, status, error) {
            alert("오류가 발생했습니다: " + error);
          }
        });

      }); // end of $('#passwordChange').on('click', function (){}

    }) // end of $(function (){}


</script>

<!-- 비밀번호 재설정 페이지 -->
<div class="container" style="max-width: 500px; margin: 0 auto; padding: 40px 20px;">
  <h2>비밀번호 재설정</h2><br><br>
  <form>

    <div style="margin-bottom: 15px;">
      <label for="newPassword"><strong>새 비밀번호</strong></label><br><br>
      <input type="password" id="newPassword" name="newPassword" style="width: 100%; padding: 8px;" required/>
      <div style="font-size: 0.9em; color: gray; margin-top: 5px;">
        (영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~16자)
      </div>
    </div>

    <div style="margin-bottom: 25px;">
      <label for="confirmPassword"><strong>새 비밀번호 확인</strong></label><br><br>
      <input type="password" id="confirmPassword" name="confirmPassword" style="width: 100%; padding: 8px;" required/>
      <span id="error">입력하신 비밀번호가 맞지않습니다.</span>
    </div>

    <div style="display: flex; justify-content: space-between;">
      <button type="button" id="passwordChange" style="padding: 10px 20px; background-color: #007BFF; color: white; border: none; border-radius: 4px;">
        비밀번호 변경
      </button>
      <button type="button" onclick="window.location.href='/'"
              style="padding: 10px 20px; background-color: #6c757d; color: white; border: none; border-radius: 4px;">
        변경 취소
      </button>
    </div>
  </form>
</div>




<jsp:include page="../include/footer.jsp"/>