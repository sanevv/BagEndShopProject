<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../include/header.jsp" />

<link rel="stylesheet" href="/aery/css/wish.css"/>
<script src="/aery/js/user/wishList.js"></script>

<div class="container mt-5">
    <div class="row">

        <!-- 좌측 마이페이지 메뉴 -->
        <div class="col-lg-3 col-md-4 border-end pe-4">
            <jsp:include page="../../include/mypageMenu.jsp" />
        </div>

        <!-- 관심상품 메인 콘텐츠 -->
        <div class="col-lg-9 col-md-8 ps-5">
            <h2 class="interest">관심 상품</h2>

            <c:choose>
                <c:when test="${empty wishList}">
                    <div class="text-center text-muted py-5">관심 상품이 없습니다.</div>
                </c:when>
                <c:otherwise>
                    <div class="wishlist-box">
                        <c:forEach var="wish" items="${wishList}" varStatus="status">
                            <div class="border rounded mb-4 p-3 wishlist-item position-relative" style="background-color: #fff;">

	    <!-- 삭제 버튼 (오른쪽 상단 X) -->
	    <a href="wishDelete.team1?productId=${wish.productId}"
	       class="position-absolute top-0 end-0 text-dark px-2 py-1"
	       style="font-size: 1.2rem; text-decoration: none;">&times;</a>
	
	    <div class="d-flex align-items-center">
	
	        <!-- 체크박스 -->
	        <div class="me-3">
	            <input type="checkbox" class="form-check-input wishCheck mt-1" value="${wish.productId}">
	        </div>
	
	        <!-- 상품 이미지 -->
	        <div class="me-4">
	            <img src="${pageContext.request.contextPath}/images/product/${empty wish.productImagePath ? 'no_image.png' : wish.productImagePath}" 
	                 class="img-thumbnail" style="width: 100px; height: auto; border: none;">
	        </div>
	
	        <!-- 상품 정보 -->
	        <div class="flex-grow-1">
	            <div class="fw-bold mb-1">${wish.productName}</div>
	            <div class="small text-muted">
	                <del>${wish.price}원</del>
	                <span class="ms-2 text-danger fw-bold">${wish.price * 0.7}원</span>
	            </div>
	            <div class="text-muted small mt-1">적립 포인트 - ${wish.price * 0.3}원</div>
	
	            <!-- 버튼 3개 하단 정렬 -->
	            <div class="mt-3">
	                <a href="productDetail.team1?productId=${wish.productId}" class="btn btn-outline-secondary btn-sm me-1">상세보기</a>
	                <a href="wishToCart.team1?productId=${wish.productId}" class="btn btn-dark btn-sm me-1">장바구니</a>
	                <a href="wishOrder.team1?productId=${wish.productId}" class="btn btn-outline-dark btn-sm">주문하기</a>
	            </div>
	        </div>
	    </div>
	</div>
                        </c:forEach>
                    </div>

                    <!-- 페이징 -->
                    <nav class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="wishList.team1?page=${currentPage - 1}">&laquo;</a>
                                </li>
                            </c:if>
                            <c:forEach var="i" begin="1" end="${totalPage}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="wishList.team1?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentPage < totalPage}">
                                <li class="page-item2">
                                    <a class="page-link" href="wishList.team1?page=${currentPage + 1}">&raquo;</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="../../include/footer.jsp" />