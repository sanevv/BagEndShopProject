<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 27.
  Time: 오전 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="<%=request.getContextPath()%>/js/cart/myCart.js" defer></script>

<%
    boolean isLogin = session.getAttribute("loginUser") != null;
    String message = "장바구니는 로그인 후 이용 가능합니다.";
%>
<script>
    <%--if (!<%=isLogin%>) {--%>
    <%--    alert("<%=message%>");--%>
    <%--    location.href = "<%=request.getContextPath()%>/test/login.up";--%>
    <%--}--%>
</script>

<jsp:include page="../include/header.jsp"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cart/style.css"/>
<div class="container">
    <div class="row">
        <!-- 좌측 상품 목록 -->
        <div class="col-md-6 mt-5">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <input type="checkbox" class="me-2" id="allCheck" checked><label for="allCheck">전체선택</label>
                </div>
                <div>
                    <a id="selectedDelete">선택삭제</a>
                </div>
            </div>

            <!-- 👜 장바구니 아이템 반복 가능 -->
            <div id="itemBody"><!-- 여기에 아이템들을담을거다 -->
            </div>

        </div>

        <div class="col-md-1"></div>
        <!-- 우측 결제 요약 -->
        <div class="col-md-5 mt-5">
            <div class="summary-box" id="paymentBody">

            </div>
        </div>
    </div>
</div>

<jsp:include page="../include/footer.jsp"/>
<%--<div class="d-flex justify-content-between">
                    <h5>결제예정금액</h5>
                    <span>총 수량 : 5 개</span>
                </div>

                <hr>
                <div class="d-flex justify-content-between mb-2">
                    <span>총 상품금액</span><span style="text-decoration: line-through">871,000원</span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span>할인 금액</span><span>500원</span>
                </div>
                <div class="summary-total d-flex justify-content-between">
                    <span>총 결제 금액</span><span>871,000원</span>
                </div>
                <div class="summary-buttons mt-4 d-grid gap-2">
                    <button class="btn btn-black">전체상품주문</button>
                    <button class="btn btn-outline-dark">선택상품주문</button>
                </div>--%>


<%--<div class="cart-item position-relative d-flex align-items-center">
    <input type="checkbox" class="me-3" checked>
    <div class="me-4 item-image detail-view">
        <img src="/src/main/webapp/images/temp.jpg" alt="제품">
    </div>
    <div class="flex-grow-1">
        <h5 class="mb-1 detail-view">Cica Inn (Navy)</h5>
        <p class="text-muted mb-1">567,000원</p>
        <div class="d-flex align-items-center justify-content-end mb-2 item-qty">
            <button class="btn btn-outline-secondary btn-sm me-2">-</button>
            <input type="text" class="form-control form-control-sm me-2" value="3" readonly>
            <button class="btn btn-outline-secondary btn-sm">+</button>
        </div>
        <div class="d-flex align-items-center justify-content-end mb-2">
            <button class="btn btn-outline-dark btn-sm me-2 detail-view">상세보기</button>
            <button class="btn btn-black btn-sm">주문하기</button>
        </div>
    </div>
    <span class="item-remove">×</span>
</div>

<!-- Frodo M -->
<div class="cart-item position-relative d-flex align-items-center">
    <input type="checkbox" class="me-3" checked>
    <div class="detail-view me-4 item-image">
        <img src="/src/main/webapp/images/temp.jpg" alt="제품">
    </div>
    <div class="flex-grow-1">
        <h5 class="mb-1 detail-view">Frodo M</h5>
        <p class="text-muted mb-1">152,000원</p>
        <div class="d-flex align-items-center justify-content-end mb-2 item-qty">
            <button class="btn btn-outline-secondary btn-sm me-2">-</button>
            <input type="text" class="form-control form-control-sm me-2" value="1" readonly>
            <button class="btn btn-outline-secondary btn-sm">+</button>
        </div>
        <div class="d-flex align-items-center justify-content-end mb-2">
            <button class="detail-view btn btn-outline-dark btn-sm me-2">상세보기</button>
            <button class="btn btn-black btn-sm">주문하기</button>
        </div>
    </div>
    <span class="item-remove">×</span>
</div>--%>