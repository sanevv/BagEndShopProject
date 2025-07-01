<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../include/header.jsp"/>

<link rel="stylesheet" href="/aery/css/user/wish.css" />

<div class="container mt-5 mb-5" style="max-width: 1200px;">
    <div class="row">

        <!-- 좌측 메뉴 -->
        <div class="col-lg-3 col-md-4">
            <jsp:include page="../../include/mypageMenu.jsp" />
        </div>

        <!-- 위시리스트 영역 -->
        <div class="col-lg-9 col-md-8 pt-4 mt-5" style="padding-left: 50px;">
	
			<span class="wishtitle h4" style="color:black; font-weight: bold;">관심 상품</span>
	
            <form name="wishForm" method="post" id="wishForm">

				<c:if test="${not empty wishProductList}">
   					<hr class="mb-3"/> 
				</c:if>

                <c:if test="${empty wishProductList}">
                    <div class="text-center py-5 text-muted" style="min-height: 300px;">
                        <p class="h5">관심 상품이 없습니다.</p>
                    </div>
                </c:if>
                
                <c:forEach var="wish" items="${wishProductList}" varStatus="status">
                    <div class="wish-item d-flex justify-content-between align-items-center border p-3 mb-3">

                        <div class="d-flex align-items-start">
                            <div>
                             	<input type="checkbox" name="wishItem" value="${wish.productId}" class="mr-3 mt-1"/>
                            	<div class="font-weight-bold">${wish.productName}</div>
								<div>
								    <c:choose>
								        <c:when test="${wish.discountedPrice != null && wish.discountedPrice < wish.price}">
								            <span class="text-muted text-decoration-line-through ml-2">
								                <fmt:formatNumber value="${wish.price}" pattern="#,###"/>원
								            </span>
								            <span class="ml-1 font-weight-bold text-danger">
								                <fmt:formatNumber value="${wish.discountedPrice}" pattern="#,###"/>원
								            </span>
								        </c:when>
								        <c:otherwise>
								            <span class="font-weight-bold">
								                <fmt:formatNumber value="${wish.price}" pattern="#,###"/>원
								            </span>
								        </c:otherwise>
								    </c:choose>
								</div>
                            </div>
                        </div>

                        <div class="d-flex flex-column align-items-center">
   							<a href="${pageContext.request.contextPath}/product/detail/${wish.productId}">
							    <img src="${pageContext.request.contextPath}/images/product/${wish.productImagePath}"
							         alt="상품 이미지"
							         width="80" height="80"
							         style="object-fit: contain; cursor: pointer;"
							         onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/product/no_image.png';" />
							</a>
    						<button type="button" class="btn btn-sm mt-2" onclick="deleteWish(${wish.productId})">×</button>
						</div>
                        
                    <div class="d-flex justify-content-end mt-4">
                        <button type="button" class="btn btn-outline-dark mr-2"
        						onclick="location.href='${pageContext.request.contextPath}/product/detail/${wish.productId}'">상세보기</button>
                        <button type="button" class="btn btn-outline-dark mr-2" onclick="wishToCart()">장바구니</button>
                        <button type="button" class="btn btn-outline-dark" onclick="wishOrder()">주문하기</button>
                    </div>
                    

                    </div>
                </c:forEach>


				<c:if test="${not empty wishProductList}">
    				<hr class="mt-3"/> 
				</c:if>
				
                <c:if test="${totalPage > 1}">
                    <div class="text-center mt-5">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <c:if test="${pageNo > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?pageNo=${pageNo - 1}">‹</a>
                                    </li>
                                </c:if>
                                <li class="page-item active"><a class="page-link" href="#">${pageNo}</a></li>
                                <c:if test="${pageNo < totalPage}">
                                    <li class="page-item">
                                        <a class="page-link" href="?pageNo=${pageNo + 1}">›</a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </c:if>

            </form>
        </div>
    </div>
</div>

<jsp:include page="../../include/footer.jsp"/>

<script>
    function deleteWish(productId) {
        if (confirm("해당 상품을 찜 목록에서 삭제하시겠습니까?")) {
            location.href = "${pageContext.request.contextPath}/wish/delete.team1?productId=" + productId;
        }
    }

    function moveToCart() {
        document.wishForm.action = "${pageContext.request.contextPath}/wishToCart.team1";
        document.wishForm.submit();
    }

    function orderSelected() {
        document.wishForm.action = "${pageContext.request.contextPath}/wishOrder.team1";
        document.wishForm.submit();
    }
</script>