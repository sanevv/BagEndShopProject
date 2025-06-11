<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 11.
  Time: 오후 4:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctxPath = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
  <%-- Required meta tags --%>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <%-- Bootstrap CSS --%>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

  <%-- 직접 만든 CSS --%>
  <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/coinPurchaseTypeChoice.css" />

  <%-- Optional JavaScript --%>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

<%--  axios--%>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<%-- 직접 만든 JS --%>
  <script type="text/javascript" src="<%= ctxPath%>/js/member/coinPurchaseTypeChoice.js" defer></script>
</head>
<body>
<div class="container">
  <h2 class="my-5">코인충천 결제방식 선택</h2>
  <p>코인충전 금액 높을수록 POINT를 무료로 많이 드립니다.</p>

  <div class="table-responsive" style="margin-top: 30px;">
    <table class="table">
      <thead>
      <tr>
        <th>금액</th>
        <th>POINT</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>
          <label class="radio-inline"><input type="radio" name="coinmoney" value="300000" />&nbsp;300,000원</label>
        </td>
        <td>
          <span>3,000</span>
        </td>
      </tr>
      <tr>
        <td>
          <label class="radio-inline"><input type="radio" name="coinmoney" value="200000" />&nbsp;200,000원</label>
        </td>
        <td>
          <span>2,000</span>
        </td>
      </tr>
      <tr>
        <td>
          <label class="radio-inline"><input type="radio" name="coinmoney" value="100000" />&nbsp;100,000원</label>
        </td>
        <td>
          <span>1,000</span>
        </td>
      </tr>
      <tr>
        <td id="error" colspan="3" align="center" style="height: 50px; vertical-align: middle; color: red;">결제종류에 따른 금액을 선택하세요!!</td>
      </tr>
      <tr>
        <td id="purchase" colspan="3" align="center" style="height: 100px; vertical-align: middle;" onclick="goCoinPayment('<%= ctxPath%>','${sessionScope.loginUser.userId}')">[충전결제하기]</td>
      </tr>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
