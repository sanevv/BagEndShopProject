<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../include/header.jsp" />

<link rel="stylesheet" href="/aery/css/productSearch/searchResult.css" />

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
    let currentSort = 'newest';
    let currentPage = 1;
    const currentPageSize = 12;
    let isLoading = false;

    const sortSelect = document.querySelector('.sort-box select');

    // 스크롤 이벤트
    function onScrollLoad() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            searchProductList();
        }
    }

    function registerScrollEvent() {
        $(window).off('scroll', onScrollLoad);
        $(window).on('scroll', onScrollLoad);
    }

    // 정렬 변경 이벤트
    sortSelect.addEventListener('change', function () {
        const selectedOption = this.options[this.selectedIndex];
        const sortValue = selectedOption.dataset.sort;
        if (!sortValue || sortValue === currentSort) return;
        currentPage = 1;
        currentSort = sortValue;
        searchProductList();
        registerScrollEvent();
    });

    // 초기 로딩
    registerScrollEvent();
    searchProductList();

    // 제품 목록 로딩
    function searchProductList() {
        if (isLoading) return;
        isLoading = true;

        axios.get(`/api/product/search`, {
            params: {
                keyword: '${keyword}',
                sort: currentSort,
                page: currentPage,
                size: currentPageSize
            }
        }).then(response => {
            const data = response.data.success?.responseData;
            if (!data) {
                isLoading = false;
                return;
            }

            const lastPage = data.totalPages;
            const items = data.items;

            if (lastPage === currentPage) {
                $(window).off('scroll', onScrollLoad);
            }

            const productList = document.querySelector('.product-list');
            if (currentPage === 1) productList.innerHTML = '';

            items.forEach(item => {
                const {
                    productId,
                    productName,
                    price,
                    thumbnail,
                    discountRate,
                    wished
                } = item;

                const discountedPrice = Math.floor(price * (1 - discountRate / 100));
                const formattedOriginal = price.toLocaleString() + '원';
                const formattedDiscounted = discountedPrice.toLocaleString() + '원';

                const li = document.createElement('li');
                li.dataset.productId = productId;
                li.innerHTML = `
                    <div class="product-img">
                        <img class="show-detail" src="${thumbnail ? thumbnail : '/images/product/test2.png'}" alt="${productName}">
                        <button type="button" class="btn-wish${wished ? ' active' : ''}"><span class="blind">좋아요</span></button>
                    </div>
                    <div class="product-info">
                        <p class="product-name show-detail">${productName}</p>
                        <div class="product-price">
                            <p class="before_price">${formattedOriginal}</p>
                            <p class="discount">
                                <span class="discout-rate">${discountRate}%</span>
                                <span class="price">${formattedDiscounted}</span>
                            </p>
                        </div>
                    </div>
                    <a href="javascript:;" class="product-link"></a>
                `;

                li.querySelectorAll('.show-detail').forEach(el => {
                    el.addEventListener('click', e => {
                        e.stopPropagation();
                        location.href = `/product/detail/${productId}`;
                    });
                });

                productList.appendChild(li);
            });

            currentPage++;
        }).catch(error => {
            console.error('Error fetching product list:', error);
        }).finally(() => {
            isLoading = false;
        });
    }

    // 찜 버튼 클릭 처리
    document.querySelector('.product-list').addEventListener('click', function (e) {
        const btn = e.target.closest('.btn-wish');
        if (btn) {
            const productId = btn.closest('li').dataset.productId;
            axios.put(`/api/product/steam`, {}, {
                params: { productId: productId }
            }).then(response => {
                btn.classList.toggle('active');
            }).catch(error => {
                console.error('Error updating wish status:', error);
                if (error.response?.status === 400) {
                    alert(error.response.data.message);
                }
            });
        }
    });
</script>


<div class="container mt-5">

    <!-- 검색어 및 결과 수 표시 -->
    <div class="search-summary" style="text-align: center; margin: 30px 0 20px; font-size: 1.1rem; color: #333;">
        <c:choose>
            <c:when test="${not empty keyword}">
                <p>
                    '<strong>${keyword}</strong>' 전체
                    <strong>${fn:length(productList)}</strong>개
                </p>
            </c:when>
        </c:choose>
    </div>

     <c:choose>
        <c:when test="${empty productList}">
            <p class="text-muted" style="text-align: center;">검색 결과가 없습니다.</p>
        </c:when>
        <c:otherwise>
            <div class="inner">
                <ul class="product-list type-4" id="productList">
                    <c:forEach var="p" items="${productList}">
                        <li data-product-id="${p.productId}">
                            <div class="product-img">
                                <img class="show-detail"
                                     src="${pageContext.request.contextPath}/images/product/test.jpeg"
                                     alt="${p.productName}" />
                                <button type="button" class="btn-wish"><span class="blind">좋아요</span></button>
                            </div>
                            <div class="product-info">
                                <p class="product-name show-detail">${p.productName}</p>
                                <div class="product-price">
                                    <p class="before_price">
                                        <fmt:formatNumber value="${p.price}" type="number" />원
                                    </p>
                                    <p class="discount">
                                        <span class="discout-rate">70%</span>
                                        <span class="price">180원</span>
                                    </p>
                                </div>
                            </div>
                            <a href="${pageContext.request.contextPath}/product/detail/${p.productId}" class="product-link"></a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<jsp:include page="../../include/footer.jsp" />

