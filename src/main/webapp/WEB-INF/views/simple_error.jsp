<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 11.
  Time: 오후 4:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

%>
<html>
<head>
  <title>예기치 못한 접근</title>
</head>
<script type="text/javascript">
  alert("<%= request.getAttribute("message") %>");
</script>
<body>
<div class="container">
  <p class="h2 text-center mt-4">장애발생</p>
  <img src="<%= request.getContextPath()%>/images/error.gif" class="img-fluid" /> <%-- 반응형 이미지 --%>
  <p class="h4 text-primary mt-3">다시 시도해주세요.</p>
</div>
</body>
</html>
