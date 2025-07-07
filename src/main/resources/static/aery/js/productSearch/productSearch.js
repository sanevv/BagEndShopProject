let currentCategory = typeof category !== 'undefined' ? category : 'all';
let currentSort = 'newest';
let currentPage = 1;
const currentPageSize = 12;
let isLoading = false;

const categories = document.querySelectorAll('.category');
const sortSelect = document.querySelector('.sort-box select');

// ✅ contextPath에서 끝 슬래시 제거
const contextPath = (typeof window.contextPath !== 'undefined' && window.contextPath) 
    ? window.contextPath.replace(/\/+$/, '') 
    : '';
const keyword = (typeof window.keyword !== 'undefined' && window.keyword) ? window.keyword : '';

// ✅ 모든 경로 조합 시 사용할 안전한 유틸 함수 (한 줄만 추가)
const buildSafeUrl = (...parts) => '/' + parts.map(p => (p || '').replace(/^\/+|\/+$/g, '')).filter(Boolean).join('/');

function onScrollLoad() {
    if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
        searchProductList();
    }
}

function registerScrollEvent() {
    $(window).off('scroll', onScrollLoad);
    $(window).on('scroll', onScrollLoad);
}

categories.forEach(category => {
    if (category.dataset.category === currentCategory) {
        category.classList.add('active');
    } else {
        category.classList.remove('active');
    }
});

categories.forEach(category => {
    category.addEventListener('click', () => {
        const selectedCategory = category.dataset.category;
        if (currentCategory === selectedCategory) return;
        categories.forEach(c => c.classList.remove('active'));
        category.classList.add('active');
        currentPage = 1;
        currentSort = 'newest';
        sortSelect.selectedIndex = 0;
        currentCategory = selectedCategory;
        searchProductList();
        registerScrollEvent();
    });
});

sortSelect.addEventListener('change', function () {
    const selectedOption = this.options[this.selectedIndex];
    const sortValue = selectedOption.dataset.sort;
    if (!sortValue || sortValue === currentSort) return;
    currentPage = 1;
    currentSort = sortValue;
    searchProductList();
    registerScrollEvent();
});

registerScrollEvent();

const searchProductList = () => {
    if (isLoading) return;
    isLoading = true;

    const safeCategory = currentCategory ? currentCategory.replace(/^\/+/, '') : 'all';

    // ✅ 안전한 URL 조합
    const apiUrl = buildSafeUrl(contextPath, 'api', 'search', safeCategory);

    axios.get(apiUrl, {
        params: {
            sort: currentSort,
            page: currentPage,
            size: currentPageSize,
            keyword: keyword
        }
    }).then(response => {
        const result = response.data;
        if (!result.success || !result.responseData || !result.responseData.items) {
            isLoading = false;
            return;
        }

        const lastPage = result.responseData.totalPages;
        if (lastPage === currentPage) {
            $(window).off('scroll', onScrollLoad);
        }

        const items = result.responseData.items;
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
                    <div class="product-back-img show-detail" style="background-image: url(${thumbnail ? thumbnail : buildSafeUrl(contextPath, 'images', 'product', 'test2.png')})"></div>
                    <button type="button" class="btn-wish${wished ? ' active' : ''}"><span class="blind">좋아요</span></button>
                </div>
                <div class="product-info">
                    <p class="product-name show-detail">${productName}</p>
                    <div class="product-price">
                        <p class="before_price">${formattedOriginal}</p>
                        <p class="discount">
                            <span class="discount-rate">${discountRate}%</span>
                            <span class="price">${formattedDiscounted}</span>
                        </p>
                    </div>
                </div>
                <a href="javascript:;" class="product-link"></a>
            `;

            li.querySelectorAll('.show-detail').forEach(el => {
                el.addEventListener('click', (e) => {
                    e.stopPropagation();
                    location.href = buildSafeUrl(contextPath, 'search', 'detail', productId);
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
};

searchProductList();

document.querySelector('.product-list').addEventListener('click', function (e) {
    const btn = e.target.closest('.btn-wish');
    if (btn) {
        axios.put(buildSafeUrl(contextPath, 'api', 'search', 'wish'), {}, {
            params: {
                productId: btn.closest('li').dataset.productId
            }
        }).then(response => {
            console.log(response);
            btn.classList.toggle('active');
        }).catch(error => {
            console.error('Error updating wish status:', error);
            if (error.response && error.response.status === 400)
                alert(error.response.data.message);
        });
    }
});