<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 5.
  Time: 오후 4:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%--%>
<%--    String ctxPath = request.getContextPath();--%>
<%--%>--%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/login/login.css"/>
<script type="text/javascript" src="<%=ctxPath%>/js/login/login.js" defer></script>

<%--로그인을 위한 폼태그<c:url value="/member/login.up"/>:은 자동으로 앞에 컨택스트 패스를 추가해줌--%>
<%--<c:if test=" ${empty sessionScope.loginUser} "> 이렇게 test쌍따옴표안에 공백넣으면 조짐--%>
<c:if test="${empty sessionScope.loginUser}">
    <form autocomplete="off" name="loginFrm" action="<c:url value="/member/login.up"/>" method="post">
        <table id="loginTbl">
            <thead>
            <tr>
                <th colspan="2">LOGIN</th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td>ID</td>
                <td><input type="text" name="userid" id="loginUserid" size="20" autocomplete="off"/></td>
            </tr>
            <tr>
                <td>암호</td>
                <td><input type="password" name="pwd" id="loginPwd" size="20" autocomplete="new-password"/></td>
            </tr>

                <%-- ==== 아이디 찾기, 비밀번호 찾기 ==== --%>
            <tr>
                <td colspan="2">
                    <a style="cursor: pointer;" data-toggle="modal" data-target="#userIdfind"
                       data-dismiss="modal">아이디찾기</a> /
                    <a style="cursor: pointer;" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal"
                       data-backdrop="static">비밀번호찾기</a>
                </td>
            </tr>

            <tr>
                <td colspan="2"><%-- 아이디 저장 체크박스 체크박스에 쿠키(cookie)를 사용할 경우 --%>
                    <input type="checkbox" id="saveid" name="saveid"/>&nbsp;<label for="saveid">아이디저장</label>
                    <button type="button" id="btnSubmit" class="btn btn-primary btn-sm ml-3">로그인</button>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <%-- ****** 아이디 찾기 Modal 시작 ****** --%>
    <div class="modal fade" id="userIdfind" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal header -->
                <div class="modal-header">
                    <h4 class="modal-title">아이디 찾기</h4>
                    <button type="button" class="close idFindClose" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div id="idFind">
                        <iframe id="iframe_idFind" style="border: none; width: 100%; height: 350px;"
                                src="<%= ctxPath%>/login/idFind.up">
                        </iframe>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger idFindClose" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>
    <%-- ****** 아이디 찾기 Modal 끝 ****** --%>
    <%-- ****** 비밀번호 찾기 Modal 시작 ****** --%>
    <div class="modal fade" id="passwdFind">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal header -->
                <div class="modal-header">
                    <h4 class="modal-title">비밀번호 찾기</h4>
                    <button type="button" class="close passwdFindClose" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div id="pwFind">
                        <iframe  id="iframe_pwdFind" style="border: none; width: 100%; height: 350px;" src="<%= ctxPath%>/login/pwdFind.up">

                        </iframe>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger passwdFindClose" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>
    <%-- ****** 비밀번호 찾기 Modal 끝 ****** --%>
</c:if>

<c:if test="${not empty sessionScope.loginUser}">
    <table style="width: 95%; height: 130px; margin: 0 auto;">
        <tr style="background-color: #f2f2f2;">
            <td style="text-align: center; padding: 20px;">
                <span style="color: blue; font-weight: bold;">${sessionScope.loginUser.userName}</span>
                [<span style="color: red; font-weight: bold;">${sessionScope.loginUser.userId}</span>]님
                <br><br>
                <div style="text-align: left; line-height: 150%; padding-left: 20px;">
                    <span style="font-weight: bold;">코인액&nbsp;:</span>&nbsp;&nbsp;<fmt:formatNumber
                        value="${sessionScope.loginUser.coin}" pattern="###,###"/> 원
                    <br>
                    <span style="font-weight: bold;">포인트&nbsp;:</span>&nbsp;&nbsp;<fmt:formatNumber
                        value="${sessionScope.loginUser.point}" pattern="###,###"/> POINT
                </div>
                <br>로그인 중...<br><br>
                [<a href="javascript:goEditMyInfo('','')">나의정보</a>]&nbsp;&nbsp;
                [<a href="javascript:goCoinPurchaseTypeChoice('${sessionScope.loginUser.userId}','<%=ctxPath%>')">코인충전</a>]
                <br><br>
                <button type="button" class="btn btn-danger btn-sm" onclick="javascript:goLogout('')">로그아웃</button>
            </td>
        </tr>
    </table>
</c:if>
