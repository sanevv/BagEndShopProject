<%--
  Created by IntelliJ IDEA.
  User: jks93
  Date: 25. 7. 3.
  Time: 오후 2:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<jsp:include page="../include/header.jsp"/>

<script type="text/javascript">

    $(document).ready(function () {

        // Admin 일 경우 select 태그의 값을 변경하여 db에 업데이트해준다.
        $('select#orderStatus').change(function (e) {

            const selectVal = $(e.target).val();

            if(selectVal === ""){
                return;
            }

            const orderId = $(e.target).data('orderid');

            $.ajax({
                url: "${pageContext.request.contextPath}/orderShow/updateStatus",
                type: "POST",
                data: {
                    orderId : orderId,
                    status : selectVal
                },
                success: function (response) {
                    console.log(response.changeOrderStatus);
                    if(response.changeOrderStatus === true){
                        alert("주문상태값이 변경되었습니다.");
                        // 다시 페이지를 새로고침 해준다
                        location.reload();
                    }
                    else{
                        alert("주문상태값 변경이 실패했습니다.");
                    }
                },
                error: function(request, status, error){
                    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                }
            })

        }) // end of $('select#orderStatus').change(function () {})


    }); // $(document).ready(function () {})



</script>

<div class="container mt-5">
    <div class="row">

        <!-- 왼쪽 사이드바 -->
        <div class="col-lg-3 col-md-4">
            <jsp:include page="../include/mypageMenu.jsp"/>
        </div>
        
        <!-- 오른쪽 주문내역 폼 -->
        <div class="col-lg-9 col-md-8">
            <div class="order-history" style="margin-top: 50px;">
            
            <div class="order-history-header" style="display: flex; justify-content: space-between; margin-bottom: 35px;">
	            <h4 class="order-history-title" style="font-size: 24px; font-weight: bold;">주문 내역</h4>
	        </div>

                <!-- 관리자 모드: 모든 회원의 주문 내역 -->
                <c:if test="${isAdmin}">
                    <h3 class="mb-4">관리자 모드 - 모든 회원 주문 내역</h3>

                    <c:if test="${empty requestScope.orderDetailsAdminList}">
                        <div style="display: flex; justify-content: center; align-items: center; height: 100vh;"><h4>주문내역이 없습니다.</h4></div>
                    </c:if>

                    <c:if test="${not empty requestScope.orderDetailsAdminList}">
                        <c:forEach var="order" items="${requestScope.orderDetailsAdminList}">
                            <div class="mb-4">
                                <h5 class="fw-bold mb-3">
                                    ${order.createdAt} - 주문번호: ${order.orderId}&nbsp;&nbsp;주문자 아이디: ${order.userId}
                                    <div class="float-end">
                                        <select class="form-select form-select-sm mt-4" id="orderStatus" data-orderid="${order.orderId}">
                                            <option value="">상태 변경</option>
                                            <option value="DELIVERY">배송중</option>
                                            <option value="COMPLETED">배송완료</option>
                                        </select>
                                    </div>
                                </h5>

                                <!-- 각 주문에 대한 상품 목록 반복 -->
                                <c:forEach var="product" items="${order.ordersProductList}">
                                    <div class="card mb-3 p-3 border">
                                        <div class="d-flex">
                                            <a href="${pageContext.request.contextPath}/product/detail/${product.orderProductId}">
                                                <img src="${product.thumbnailPath}" alt="상품 이미지" width="100" height="100" class="me-3" style="margin-right: 30px">
                                            </a>

                                            <div class="flex-grow-1">
                                                <a href="${pageContext.request.contextPath}/product/detail/${product.orderProductId}" style="color: black; text-decoration: none;">
                                                        ${product.productName}
                                                </a>
                                                <div class="text-muted small">${product.productSize}</div>
                                                <div class="fw-bold mt-2" style="text-decoration: line-through;">${product.atPrice}원</div>
                                                <div class="fw-bold mt-2">
                                                    <fmt:formatNumber value="${product.atPrice * product.atDiscountRate}" type="number" maxFractionDigits="0" />원&nbsp;&nbsp;${product.quantity}개
                                                </div>

                                                <div class="text-muted small">
                                                    <c:choose>
                                                        <c:when test="${order.status == 'READY'}">준비중</c:when>
                                                        <c:when test="${order.status == 'DELIVERY'}">배송중</c:when>
                                                        <c:when test="${order.status == 'COMPLETED'}">배송완료</c:when>
                                                        <c:otherwise>${order.status}</c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:if>
                </c:if>

                <!-- 일반 회원 모드: 자신의 주문 내역만 -->
                <c:if test="${!isAdmin}">
                    <!-- 주문일자 -->
                    <c:if test="${empty requestScope.orderDetailsList}">
                        <div style="display: flex; justify-content: center; align-items: center; height: 100vh;"><h4>주문내역이 없습니다.</h4></div>
                    </c:if>

                    <c:if test="${not empty requestScope.orderDetailsList}">
                        <c:forEach var="order" items="${requestScope.orderDetailsList}">
                            <div class="mb-4">
                                <h5 class="fw-bold mb-3">${order.createdAt}</h5>

                                <!-- 각 주문에 대한 상품 목록 반복 -->
                                <c:forEach var="product" items="${order.ordersProductList}">
                                    <div class="card mb-3 p-3 border">
                                        <div class="d-flex">
                                            <a href="${pageContext.request.contextPath}/product/detail/${product.orderProductId}">
                                                <img src="${product.thumbnailPath}" alt="상품 이미지" width="100" height="100" class="me-3" style="margin-right: 30px">
                                            </a>

                                            <div class="flex-grow-1">
                                                <a href="${pageContext.request.contextPath}/product/detail/${product.orderProductId}" style="color: black; text-decoration: none;">
                                                        ${product.productName}
                                                </a>
                                                <div class="text-muted small">${product.productSize}</div>
                                                <div class="fw-bold mt-2" style="text-decoration: line-through;">${product.atPrice}원</div>
                                                <div class="fw-bold mt-2">
                                                    <fmt:formatNumber value="${product.atPrice * product.atDiscountRate}" type="number" maxFractionDigits="0" />원&nbsp;&nbsp;${product.quantity}개
                                                </div>

                                                <c:choose>
                                                    <c:when test="${order.status == 'READY'}">
                                                        <div class="text-muted small">준비중</div>
                                                    </c:when>
                                                    <c:when test="${order.status == 'DELIVERY'}">
                                                        <div class="text-muted small">배송중</div>
                                                    </c:when>
                                                    <c:when test="${order.status == 'COMPLETED'}">
                                                        <div class="text-muted small">배송완료</div>
                                                    </c:when>
                                                </c:choose>

                                                <c:choose>
                                                    <c:when test="${order.status == 'DELIVERY' || order.status == 'COMPLETED'}">
                                                        <div class="mt-2">
                                                            <a href="${pageContext.request.contextPath}/review/write?productId=${product.orderProductId}"
                                                               style="color: black; text-decoration: none;">
                                                                후기작성
                                                            </a>
                                                        </div>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
</div>


<jsp:include page="../include/footer.jsp"/>
