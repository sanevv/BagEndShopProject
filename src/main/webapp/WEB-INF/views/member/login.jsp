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

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login/oauth.css"/>


<script type="text/javascript" src="<%=request.getContextPath()%>/js/login/myUserLogin.js" defer></script>
<script type="text/javascript" defer>


    $(function () {

        if (${empty sessionScope.loginUser}) {

            const loginUser = localStorage.getItem('checkSaveId');

            if(loginUser != null){
                $('input#userEmail').val(loginUser);
                $('input#checkSaveId').prop('checked', true);
            }

        }


        //소셜로그인버튼 이벤트등록
        const socialButtons = document.querySelectorAll('.social-btn');
        console.log('내려옴')
        socialButtons.forEach(button => {
            console.log('포이치들옴')
            button.addEventListener('click', async function () {
                    console.log('이벤트발생')
                    const provider = this.getAttribute('data-provider');
                    const authUrl = await requestAuthUrl(provider);
                    console.log(authUrl);
                }
            )
        })
        //소셜로그인 요청 URL 생성
        requestAuthUrl = async (provider) => {
            const apiUrl = `${pageContext.request.contextPath}/api/oauth/\${provider}/authorize`;

            const response = await axios.get(apiUrl);
            if (!response.ok)
                throw new Error(`Error fetching auth URL: \${response.statusText}`);

            return response.data.success.responseData;
        }



    }) // end of $(function (){}

</script>

<c:if test="${empty sessionScope.loginUser}">
    <div class="container mt-5 d-flex justify-content-center">
        <div class="card p-4 shadow" style="width: 100%; max-width: 400px;">
            <form id="memberLoginFrm" action="/api/member/login"  method="post">
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
                                    <input type="checkbox" class="form-check-input" id="checkSaveId"
                                           name="checkSaveId"/>
                                    <label class="form-check-label" for="checkSaveId">아이디 저장</label>
                                </div>
                                <div class="small">
                                    <a href="<%= request.getContextPath()%>/test/findEmail.up" class="me-1">아이디</a> /
                                    <a href="<%= request.getContextPath()%>/test/findPassword.up" class="ms-1">비밀번호</a>
                                    찾기
                                </div>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" class="border-0">
                            <button type="button" id="btnSubmit" class="btn btn-dark w-100 mt-2">로그인</button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div class="social-login">
                    <p>또는 소셜 계정으로 로그인</p>
                    <div class="social-icons">
                        <a class="social-btn" data-provider="kakao"><img
                                src="${pageContext.request.contextPath}/images/oauth/kakao.png" alt="Kakao"></a>
                        <a class="social-btn" data-provider="naver"><img
                                src="${pageContext.request.contextPath}/images/oauth/naver.png" alt="Naver"></a>
                        <a class="social-btn" data-provider="github"><img
                                src="${pageContext.request.contextPath}/images/oauth/github.png" alt="GitHub"></a>
                        <a class="social-btn" data-provider="google"><img
                                src="${pageContext.request.contextPath}/images/oauth/google.png" alt="Google"></a>
                        <a class="social-btn" data-provider="microsoft"><img
                                src="${pageContext.request.contextPath}/images/oauth/microsoft.png" alt="Microsoft"></a>
                        <a class="social-btn" data-provider="facebook"><img
                                src="${pageContext.request.contextPath}/images/oauth/facebook.png" alt="Facebook"></a>
                        <a class="social-btn" data-provider="twitter"><img
                                src="${pageContext.request.contextPath}/images/oauth/twitter.png" alt="Twitter"></a>
                    </div>
                </div>

                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/memberRegister.team1">회원가입</a>
                </div>
            </form>
        </div>
    </div>

</c:if>

<c:if test="${not empty sessionScope.loginUser}">

</c:if>


<jsp:include page="../include/footer.jsp"/>