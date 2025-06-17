<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../header2.jsp"/>

<style>
    .table {
        width: 70%;
        margin: 0 auto;
    }

    .table th {
        text-align: right;
    }

    table.table-bordered > tbody > tr > td:nth-child(1) {
        width: 25%;
        font-weight: bold;
        text-align: right;
    }
</style>

<script type="text/javascript">
    const userIdParam = '<%=request.getAttribute("userId")%>';
    const prevPage = '<%=request.getAttribute("prevPage")%>';
    const prevSize = '<%=request.getAttribute("prevSize")%>';
    const prevSearchType = '<%=request.getAttribute("prevSearchType")%>';
    const prevSearchValue = '<%=request.getAttribute("prevSearchValue")%>';


    $(function () {


    });


</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin/memberDetail.js" defer></script>

<div class="container mt-5">
    <h4 class="text-center mb-4" id="detailTitle"></h4>
    <table class="table table-bordered">
        <colgroup>
            <col style="width: 30%">
            <col style="width: auto">
        </colgroup>

        <tbody>
        <tr>
            <th>아이디 :</th>
            <td><span id="userIdInput"></span></td>
        </tr>
        <tr>
            <th>회원명 :　</th>
            <td><span id="userNameInput"></span></td>
        </tr>
        <tr>
            <th>이메일 :　</th>
            <td><span id="emailInput"></span></td>
        </tr>
        <tr>
            <th>휴대폰 :　</th>
            <td><span id="phoneNumberInput"></span></td>
        </tr>
        <tr>
            <th>우편번호 :　</th>
            <td><span id="zipCodeInput"></span></td>
        </tr>
        <tr>
            <th>주소 :　</th>
            <td><span id="addressInput"></span></td>
        </tr>
        <tr>
            <th>성별 :　</th>
            <td><span id="genderInput"></span></td>
        </tr>
        <tr>
            <th>생년월일 :　</th>
            <td><span id="birthInput"></span></td>
        </tr>
        <tr>
            <th>만나이 :　</th>
            <td><span id="ageInput"></span> 세</td>
        </tr>
        <tr>
            <th>코인액 :　</th>
            <td><span id="coinInput"></span> 원</td>
        </tr>
        <tr>
            <th>포인트 :　</th>
            <td><span id="pointInput"></span> POINT</td>
        </tr>
        <tr>
            <th>가입일자 :　</th>
            <td><span id="registerAtInput"></span></td>
        </tr>
        </tbody>
    </table>

    <%-- ==== 휴대폰 SMS(문자) 보내기 ==== --%>
    <div class="border my-5 text-center" style="width: 60%; margin: 0 auto;">
        <p class="h5 bg-info text-white">
            &gt;&gt;&nbsp;&nbsp;휴대폰 SMS(문자) 보내기 내용 입력란&nbsp;&nbsp;&lt;&lt;
        </p>
        <div class="mt-4 mb-3">
            <span class="bg-danger text-white" style="font-size: 14pt;">문자발송 예약일자</span>
            <input type="date" id="reservedate" class="mx-2"/>
            <input type="time" id="reservetime"/>
        </div>
        <div style="display: flex;">
            <div style="border: solid 0px red; width: 81%; margin: auto;">
                <textarea rows="4" id="smsContent" style="width: 100%;"></textarea>
            </div>
            <div style="border: solid 0px blue; width: 19%; margin: auto;">
                <button id="btnSend" class="btn btn-secondary">문자전송</button>
            </div>
        </div>
        <div id="smsResult" class="p-3"></div>
    </div>

    <div class="text-center mb-5">
        <button type="button" class="btn btn-secondary" onclick="javascript:location.href='memberList.up'">회원목록[처음으로]
        </button>
        <button type="button" class="btn btn-success mx-5" onclick="javascript:history.back()">회원목록[history.back()]
        </button>
    </div>


<jsp:include page="../../footer2.jsp"/>