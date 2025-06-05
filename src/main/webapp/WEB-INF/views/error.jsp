<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 4.
  Time: 오전 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header1.jsp" />
<div class="container">
  <p class="h2 text-center mt-4">장애발생</p>
  <img src="<%= request.getContextPath()%>/images/error.gif" class="img-fluid" /> <%-- 반응형 이미지 --%>
  <p class="h4 text-primary mt-3">빠른 복구를 위해 최선을 다하겠습니다.</p>
</div>
<jsp:include page="footer1.jsp" />
</body>
</html>
