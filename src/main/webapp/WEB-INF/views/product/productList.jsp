<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Header --%>
<jsp:include page="../include/header.jsp" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product/product.css">

<!-- main contents -->
<main id="main">
    <div class="inner">
        <div class="product-top">
            <div class="product-category">
                <ul class="category-list">
                    <li><a href="javascript:;" class="active">MESSENGER</a></li>
                    <li><a href="javascript:;">BACKPACK</a></li>
                    <li><a href="javascript:;">CROSS</a></li>
                </ul>
            </div>
            <div class="product-sort">
                <select name="" id="" class="select-option">
                    <option value="">입맛대로 골라보셔요</option>
                    <option value="">신상품</option>
                    <option value="">낮은가격</option>
                    <option value="">높은가격</option>
                </select>
            </div>
        </div>
        <ul class="product-list type-4">
            <li>
                <div class="product-img">
                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">
                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name">국내산 이시후(냉동)</p>
                    <div class="product-price">
                        <p class="before_price">12,900원</p>
                        <p class="discount">
                            <span class="discout-rate">30%</span>
                            <span class="price">9,000원</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            </li>
            <li>
                <div class="product-img">
                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">
                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name">국내산 전경수(매우싱싱)</p>
                    <div class="product-price">
                        <p class="before_price">12,900원</p>
                        <p class="discount">
                            <span class="discout-rate">30%</span>
                            <span class="price">9,000원</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            </li>
            <li>
                <div class="product-img">
                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">
                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name">국내산 전경수(매우싱싱)</p>
                    <div class="product-price">
                        <p class="before_price">12,900원</p>
                        <p class="discount">
                            <span class="discout-rate">30%</span>
                            <span class="price">9,000원</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            </li>
            <li>
                <div class="product-img">
                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">
                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name">국내산 전경수(매우싱싱)</p>
                    <div class="product-price">
                        <p class="before_price">12,900원</p>
                        <p class="discount">
                            <span class="discout-rate">30%</span>
                            <span class="price">9,000원</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            </li>
            <li>
                <div class="product-img">
                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">
                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name">국내산 전경수(매우싱싱)</p>
                    <div class="product-price">
                        <p class="before_price">12,900원</p>
                        <p class="discount">
                            <span class="discout-rate">30%</span>
                            <span class="price">9,000원</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            </li>
            <li>
                <div class="product-img">
                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">
                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name">국내산 전경수(매우싱싱)</p>
                    <div class="product-price">
                        <p class="before_price">12,900원</p>
                        <p class="discount">
                            <span class="discout-rate">30%</span>
                            <span class="price">9,000원</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            </li>
            <li>
                <div class="product-img">
                    <img src="${pageContext.request.contextPath}/images/product/test.jpeg" alt="">
                    <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name">국내산 전경수(매우싱싱)</p>
                    <div class="product-price">
                        <p class="before_price">12,900원</p>
                        <p class="discount">
                            <span class="discout-rate">30%</span>
                            <span class="price">9,000원</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            </li>
        </ul>
    </div>
</main>
<!-- //main contents -->


<%-- Header --%>
<jsp:include page="../include/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/product/product.js"></script>
