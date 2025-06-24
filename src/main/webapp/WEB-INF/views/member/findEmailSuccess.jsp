<%--
  Created by IntelliJ IDEA.
  User: jks93
  Date: 25. 6. 24.
  Time: 오후 4:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../include/header.jsp"/>


<style>
    .result-container {
        max-width: 600px;
        margin: 100px auto;
        padding: 40px;
        border-radius: 12px;
        background-color: #fff;
        text-align: center;
    }

    .result-title {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
    }

    .result-desc {
        font-size: 16px;
        color: #333;
        margin-bottom: 30px;
    }

    .id-box {
        text-align: left;
        font-size: 16px;
        background-color: #fafafa;
        padding: 20px;
        border-radius: 8px;
        margin-bottom: 30px;
    }

    .btn-group {
        display: flex;
        justify-content: center;
        gap: 20px;
    }

    .btn-black {
        background-color: #000;
        color: #fff;
        border: none;
        padding: 12px 30px;
        border-radius: 8px;
        font-size: 16px;
        font-weight: bold;
        text-decoration: none;
    }

    .btn-black:hover {
        background-color: #222;
    }
</style>

<div class="result-container">
    <div class="result-title">아이디 찾기</div>
    <div class="result-desc">
        아이디 찾기가 완료되었습니다.<br>
        가입된 아이디가 총 <strong>${idCount}</strong>개 있습니다.
    </div>

    <div class="id-box">
        <div style="font-size:14px; color:#888;">아이디 찾기 결과</div>
        <label>
            <input type="radio" name="foundId" checked>
            <strong>${maskedId}</strong>
            <span style="color: #555;"> ( ${regDate} 가입 )</span>
        </label>
    </div>

    <div class="btn-group">
        <a href="/test/login.up" class="btn-black">로그인</a>
        <a href="" class="btn-black">비밀번호 찾기</a>
    </div>
</div>



<jsp:include page="../include/footer.jsp"/>
