<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Header --%>
<jsp:include page="../../include/header.jsp" />

<%-- Styles --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/aery/css/productSearch/productSearch.css">

<%-- 전역 변수 선언 (JS에서 접근할 수 있게) --%>
<script>
    window.contextPath = '${pageContext.request.contextPath}';
    window.keyword = '<c:out value="${param.keyword}" />';
</script>

<%-- Scripts --%>
<script src="${pageContext.request.contextPath}/aery/js/productSearch/productSearch.js" defer></script>
<script src="${pageContext.request.contextPath}/js/product/product.js" defer></script>

<!-- main contents -->
<main id="main">
    <div class="header">
        <div class="categories">
            <div class="category" data-category="all">ALL</div>
            <div class="category" data-category="messenger">MESSENGER</div>
            <div class="category" data-category="cross">CROSS</div>
            <div class="category" data-category="backpack">BACKPACK</div>
        </div>
        <div class="sort-box">
            <label>
                <select>
                    <option data-sort="">SORT BY</option>
                    <option data-sort="newest">신상품</option>
                    <option data-sort="price_asc">낮은가격</option>
                    <option data-sort="price_desc">높은가격</option>
                </select>
            </label>
        </div>
    </div>

    <div class="inner">
        <ul class="product-list type-4" id="productList">
            <!-- JS에서 동적으로 상품이 렌더링됩니다 -->
        </ul>
    </div>
</main>

<%-- Footer --%>
<jsp:include page="../../include/footer.jsp" />