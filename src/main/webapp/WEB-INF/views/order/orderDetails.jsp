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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/order/orderDetails.js"/>

<script type="text/javascript">


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


                          <!-- 상품 반복 영역 끝 -->
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</div>


<jsp:include page="../include/footer.jsp"/>
