<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 7. 1.
  Time: 오전 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ObjectMapper mapper = new ObjectMapper();
    String paramsJson = mapper.writeValueAsString(request.getAttribute("params"));
%>
<script>
    const provider = "${provider}"
    const providerValue ="${providerValue}";

    <%--const test = JSON.parse('${params}') 여기는 에러가 난다--%>
    <%--VM14:1  Uncaught SyntaxError: Unexpected token 'c', "com.github"... is not valid JSON
        at JSON.parse (<anonymous>)
        at callback?code=testtest:7:23--%>

    <%--console.log(typeof '${params}  '+'${params}')
    string 타입이고 객체 경로가찍힘 com.github.semiprojectshop.web.sihu.dto.oauth.request.KakaoLoginParams@5bf87b0--%>

    // console.log(JSON.parse(params)); 여기 역시 에러가난다.
    // VM13:1  Uncaught SyntaxError: "[object Object]" is not valid JSON
    // at JSON.parse (<anonymous>)
    //     at callback?code=testtest:16:22

    <%--console.log('<%=paramsJson%>');
    {"authorizationCode":"testtest"} 이렇게 찍힘 문자열로
    이걸 바로 JSON.parse하면 위에 같이 에러가난다.--%>

    //따라서 replace를 이용해서 JSON.parse를 사용한다. 이스케이프처리하고 그것을 '로 감싼다.
    const params = JSON.parse('<%= paramsJson.replace("'", "\\'") %>');
    console.log(params);
    // {
    //     "authorizationCode": "testtest"
    // } 객체로 변환되어 정상출력

    (async function requestLoginOrRegister() {//템플릿 리터럴 사용을위해 \역슬래시 한번
        const response = await fetch(`/api/oauth/\${provider}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params)
        });
        const data = await response.json(); // 응답 데이터를 JSON으로 파싱

        if (response.status === 200) {
            const isConnection = response.headers.get('X-Is-Connection') === 'true';
            console.log('Login or Register successful:', data);
            //로그인 성공후 부모창의 함수호출
            if (window.opener && !window.opener.closed)
                window.opener.handleLoginSuccess(isConnection, data.success.responseData, providerValue);
            self.close();
            // Redirect or handle success
        } else if (response.status === 201) {
            console.log('User registered successfully:', data);
            if (window.opener && !window.opener.closed)
                window.opener.handleSignUpRequest(data.success.responseData);
            self.close();
            // Redirect to login or handle registration success
        } else {
            console.error('Login or Register failed:', response.statusText);
            // Handle error
        }
    })();


</script>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
