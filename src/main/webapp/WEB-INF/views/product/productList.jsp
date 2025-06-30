<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Header --%>
<jsp:include page="../include/header.jsp" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product/product.css">
<script src="<%=request.getContextPath()%>/js/product/productList.js" defer></script>
<script>
    const category = '${category}';
</script>


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

<!-- main contents -->
<main id="main">
    <div class="inner">
<%--        <h2 class="">zz</h2>--%>
        <ul class="product-list type-4" id="productList">
            <!-- 여기에 JS로 상품 li들이 추가됨 -->
        </ul>
<%--        <ul class="product-list type-4">--%>
<%--            <li>--%>
<%--                <div class="product-img">--%>
<%--                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">--%>
<%--                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>--%>
<%--                </div>--%>
<%--                <div class="product-info">--%>
<%--                    <p class="product-name">국내산 이시후(천재)</p>--%>
<%--                    <div class="product-price">--%>
<%--                        <p class="before_price">12,900원</p>--%>
<%--                        <p class="discount">--%>
<%--                            <span class="discout-rate">30%</span>--%>
<%--                            <span class="price">9,000원</span>--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <a href="javascript:;" class="product-link"></a>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <div class="product-img">--%>
<%--                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">--%>
<%--                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>--%>
<%--                </div>--%>
<%--                <div class="product-info">--%>
<%--                    <p class="product-name">국내산 전경수(매우싱싱)</p>--%>
<%--                    <div class="product-price">--%>
<%--                        <p class="before_price">12,900원</p>--%>
<%--                        <p class="discount">--%>
<%--                            <span class="discout-rate">30%</span>--%>
<%--                            <span class="price">9,000원</span>--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <a href="javascript:;" class="product-link"></a>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <div class="product-img">--%>
<%--                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">--%>
<%--                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>--%>
<%--                </div>--%>
<%--                <div class="product-info">--%>
<%--                    <p class="product-name">국내산 전경수(매우싱싱)</p>--%>
<%--                    <div class="product-price">--%>
<%--                        <p class="before_price">12,900원</p>--%>
<%--                        <p class="discount">--%>
<%--                            <span class="discout-rate">30%</span>--%>
<%--                            <span class="price">9,000원</span>--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <a href="javascript:;" class="product-link"></a>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <div class="product-img">--%>
<%--                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">--%>
<%--                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>--%>
<%--                </div>--%>
<%--                <div class="product-info">--%>
<%--                    <p class="product-name">국내산 전경수(매우싱싱)</p>--%>
<%--                    <div class="product-price">--%>
<%--                        <p class="before_price">12,900원</p>--%>
<%--                        <p class="discount">--%>
<%--                            <span class="discout-rate">30%</span>--%>
<%--                            <span class="price">9,000원</span>--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <a href="javascript:;" class="product-link"></a>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <div class="product-img">--%>
<%--                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">--%>
<%--                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>--%>
<%--                </div>--%>
<%--                <div class="product-info">--%>
<%--                    <p class="product-name">국내산 전경수(매우싱싱)</p>--%>
<%--                    <div class="product-price">--%>
<%--                        <p class="before_price">12,900원</p>--%>
<%--                        <p class="discount">--%>
<%--                            <span class="discout-rate">30%</span>--%>
<%--                            <span class="price">9,000원</span>--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <a href="javascript:;" class="product-link"></a>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <div class="product-img">--%>
<%--                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">--%>
<%--                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>--%>
<%--                </div>--%>
<%--                <div class="product-info">--%>
<%--                    <p class="product-name">국내산 전경수(매우싱싱)</p>--%>
<%--                    <div class="product-price">--%>
<%--                        <p class="before_price">12,900원</p>--%>
<%--                        <p class="discount">--%>
<%--                            <span class="discout-rate">30%</span>--%>
<%--                            <span class="price">9,000원</span>--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <a href="javascript:;" class="product-link"></a>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <div class="product-img">--%>
<%--                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">--%>
<%--                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>--%>
<%--                </div>--%>
<%--                <div class="product-info">--%>
<%--                    <p class="product-name">국내산 전경수(매우싱싱)</p>--%>
<%--                    <div class="product-price">--%>
<%--                        <p class="before_price">12,900원</p>--%>
<%--                        <p class="discount">--%>
<%--                            <span class="discout-rate">30%</span>--%>
<%--                            <span class="price">9,000원</span>--%>
<%--                        </p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <a href="javascript:;" class="product-link"></a>--%>
<%--            </li>--%>
<%--        </ul>--%>
    </div>
</main>
<!-- //main contents -->


<%-- Header --%>
<jsp:include page="../include/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/product/product.js"></script>
