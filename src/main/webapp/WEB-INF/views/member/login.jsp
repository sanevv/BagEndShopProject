<%--
  Created by IntelliJ IDEA.
  User: jks93
  Date: 25. 6. 20.
  Time: 오후 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../include/header.jsp"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/login/myUserLogin.js" defer></script>
<script type="text/javascript" defer>

    $(function (){

        if(${empty sessionScope.loginUser}){

            const loginUser = localStorage.getItem('checkSaveId');

            if(loginUser != null){
                $('input#userEamil').val(loginUser);
                $('input#checkSaveId').prop('checked', true);
            }

        }

    }) // end of $(function (){}

</script>

<c:if test="${empty sessionScope.loginUser}">
    <div class="container mt-5 d-flex justify-content-center">
        <div class="card p-4 shadow" style="width: 100%; max-width: 400px;">
            <form id="memberLoginFrm" action="/test/login.up" method="post">
                <table class="table border-0 mb-0">
                    <tbody>
                    <tr>
                        <th class="border-0 align-middle" style="width: 100px;">
                            <label for="userEmail" class="form-label mb-0">아이디</label>
                        </th>
                        <td class="border-0">
                            <input id="userEmail" name="userEmail" type="text" class="form-control" placeholder="아이디를 입력하세요" />
                        </td>
                    </tr>

                    <tr>
                        <th class="border-0 align-middle">
                            <label for="loginPwd" class="form-label mb-0">비밀번호</label>
                        </th>
                        <td class="border-0">
                            <input id="loginPwd" name="pwd" type="password" class="form-control" placeholder="비밀번호를 입력하세요" />
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" class="border-0">
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="checkSaveId" name="checkSaveId" />
                                    <label class="form-check-label" for="checkSaveId">아이디 저장</label>
                                </div>
                                <div class="small">
                                    <a href="<%= request.getContextPath()%>/test/findEmail.up" class="me-1">아이디</a> /
                                    <a href="#" class="ms-1">비밀번호</a> 찾기
                                </div>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" class="border-0">
                            <button type="submit" id="btnSubmit" class="btn btn-dark w-100 mt-2">로그인</button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div class="text-center mt-3">
                    <a href="#">회원가입</a>
                </div>
            </form>
        </div>
    </div>

</c:if>

<c:if test="${not empty sessionScope.loginUser}">

</c:if>








<jsp:include page="../include/footer.jsp"/>