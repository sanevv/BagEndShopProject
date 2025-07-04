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

    function onScrollLoad() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            searchProductList();
        }
    }

    function registerScrollEvent() {
        $(window).off('scroll', onScrollLoad);
        $(window).on('scroll', onScrollLoad);
    }

    // 초기 실행
    $(document).ready(function () {
        registerScrollEvent();
        searchProductList();
    });

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

            if (currentPage === 1) {
                const summary = document.querySelector('.search-summary');
                if (items.length > 0) {
                    summary.innerHTML = `'${'${keyword}'}' 전체 <strong>${data.totalCount}</strong>개`;
                } else {
                    summary.innerHTML = ""; // 개수 출력 생략
                    document.querySelector('.no-result').style.display = 'block';
                }
            }

            if (lastPage === currentPage) {
                $(window).off('scroll', onScrollLoad);
            }

            const productList = document.querySelector('.product-list');
            if (currentPage === 1) productList.innerHTML = '';

            items.forEach(item => {
                const { productId, productName, price, thumbnail, discountRate, wished } = item;
                const discountedPrice = Math.floor(price * (1 - discountRate / 100));
                const formattedOriginal = price.toLocaleString() + '원';
                const formattedDiscounted = discountedPrice.toLocaleString() + '원';

                const li = document.createElement('li');
                li.dataset.productId = productId;
                li.innerHTML = `
                    <div class="product-img">
                        <img class="show-detail" src="${thumbnail || '/images/product/test2.png'}" alt="${productName}">
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
                    <a href="/product/detail/${productId}" class="product-link"></a>
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
            console.error('상품 조회 실패:', error);
        }).finally(() => {
            isLoading = false;
        });
    }

    // 찜 토글 처리
    document.addEventListener('click', function (e) {
        const btn = e.target.closest('.btn-wish');
        if (btn) {
            const productId = btn.closest('li').dataset.productId;
            axios.put(`/api/product/steam`, {}, {
                params: { productId }
            }).then(() => {
                btn.classList.toggle('active');
            }).catch(error => {
                console.error('찜 실패:', error);
                if (error.response?.status === 400) {
                    alert(error.response.data.message);
                }
            });
        }
    });
</script>

<div class="container mt-5">

    <!-- 검색어 + 개수 표시 (상품이 있을 경우만 채워짐) -->
    <div class="search-summary" style="text-align: center; margin: 30px 0 20px; font-size: 1.1rem; color: #333;"></div>

    <!-- 검색 결과 없음 메시지 -->
    <p class="no-result text-muted" style="text-align: center; display: none;">검색 결과가 없습니다.</p>

    <!-- 상품 목록 -->
    <div class="inner">
        <ul class="product-list type-4" id="productList"></ul>
    </div>
</div>

<jsp:include page="../../include/footer.jsp" />