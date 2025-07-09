<%--
  Created by IntelliJ IDEA.
  User: sihu
  Date: 25. 6. 29.
  Time: 오후 2:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../include/header.jsp"/>
<script>
    const orderProductRequests = ${orderProductRequestsJson};
    const ctxPath = '${pageContext.request.contextPath}';
</script>
<style>
    body {
        font-family: 'Noto Sans KR', sans-serif;
        background-color: #f8f9fa;
        padding: 40px;
    }

    .container {
        max-width: 900px;
        margin: 25px auto 0 auto;
        background: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);

    }

    .order-item {
        display: flex;
        align-items: center;
        border-bottom: 1px solid #e9ecef;
        padding: 20px 0;
    }

    .order-item img {
        width: 80px;
        height: 80px;
        object-fit: cover;
        margin-right: 20px;
    }

    .item-info {
        flex: 1;
    }

    .item-name {
        font-weight: bold;
        font-size: 18px;
        margin-bottom: 5px;
    }

    .item-price {
        display: flex;
        align-items: center;
        font-size: 15px;
    }

    .item-price .original {
        text-decoration: line-through;
        color: #999;
        margin-right: 10px;
    }

    .item-price .discounted {
        color: #ff4d4f;
        font-weight: bold;
    }
    .discounted-quantity {
        margin-top: 8px;
        color: #ff4d4f;
        font-weight: bold;
        font-size: 14px;
    }

    .item-quantity {
        margin-top: 5px;
        color: #666;
        margin-right: 10px;
    }

    .summary {
        margin-top: 40px;
        padding-top: 20px;
        border-top: 2px solid #000;
    }

    .summary-row {
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
        font-size: 16px;
    }

    .summary-row.total {
        font-weight: bold;
        font-size: 20px;
        color: #000;
    }

    .btn-box {
        margin-top: 30px;
        display: flex;
        justify-content: flex-end;
    }

    .btn-box button {
        padding: 10px 25px;
        font-size: 16px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .btn-pay {
        background-color: #000;
        color: #fff;
        margin-left: 10px;
    }

    .btn-cancel {
        background-color: #e9ecef;
        color: #333;
    }
</style>
</head>
<body>
<main id="main">
    <div class="container">
        <h2>주문 상품 확인</h2>

        <div id="orderItems"></div>

        <div class="summary">
            <div class="summary-row">
                <span>총 상품금액</span>
                <span id="totalPrice" class="original"></span>
            </div>
            <div class="summary-row">
                <span>할인 금액</span>
                <span id="discountPrice"></span>
            </div>
            <div class="summary-row total">
                <span>총 결제 금액</span>
                <span id="finalPrice"></span>
            </div>
        </div>

        <div class="btn-box">
            <button class="btn-cancel" onclick="history.back()">취소</button>
            <button class="btn-pay" onclick="requestPayment()">결제하기</button>
        </div>
    </div>
</main>

<script type="text/javascript" src='${pageContext.request.contextPath}/js/order/orderConfirm.js' defer></script>

<jsp:include page="../include/footer.jsp"/>
