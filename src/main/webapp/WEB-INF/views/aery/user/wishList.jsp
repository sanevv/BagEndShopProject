<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../../include/header.jsp" />

<link rel="stylesheet" type="text/css" href="/aery/css/user/wishList.css" />
<script type="text/javascript" src="/aery/js/user/wishList.js"></script>

<style type="text/css">
</style>

<div class="container mt-5 d-flex">
    
    <%-- 좌측 마이페이지 메뉴 --%>
    <jsp:include page="../../include/mypageMenu.jsp" />

    <div class="wishlist-content col-lg-7 col-md-8">
        <h3 class="mb-4">관심 상품</h3>

        <form name="wishForm" method="post">
            <table class="table table-bordered table-hover">
                <thead class="thead-light">
                    <tr>
                        <th style="width: 5%;"><input type="checkbox" id="checkAll" /></th>
                        <th style="width: 15%;">이미지</th>
                        <th style="width: 40%;">상품명</th>
                        <th style="width: 10%;">가격</th>
                        <th style="width: 30%;">기능</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="wish" items="${wishList}">
                        <tr>
                            <td><input type="checkbox" name="product_id" value="${wish.product_id}" /></td>

      						<td>
                                <img src="${pageContext.request.contextPath}/images/product/${wish.productImagePath}"
                                     onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/product/no_image.png';"
                                     alt="상품 이미지" width="80" />
                            </td>
                            
                            
                            <td>
                                <a href="/product/detail/?product_id=${wish.product_id}">
                                    ${wish.product_name}
                                </a>
                            </td>
                            <td>${wish.price}원</td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-primary add-cart" data-id="${wish.product_id}">장바구니</button>
                                <button type="button" class="btn btn-sm btn-outline-success order-now" data-id="${wish.product_id}">주문하기</button>
                                <button type="button" class="btn btn-sm btn-outline-danger delete-wish" data-id="${wish.product_id}">삭제</button>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty wishList}">
                        <tr>
                            <td colspan="5" class="text-center">관심 상품이 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

            <div class="mt-3 text-right">
                <button type="button" class="btn btn-danger btn-sm" id="deleteSelected">선택 삭제</button>
            </div>

            <%-- 페이징 영역 --%>
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    ${pageBar}
                </ul>
            </nav>
        </form>
    </div>
</div>



<jsp:include page="../../include/footer.jsp" />



