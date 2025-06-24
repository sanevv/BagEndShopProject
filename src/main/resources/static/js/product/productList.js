let currentCategory = 'all';
let currentSort = 'newest';
let currentPage = 1;
const currentPageSize = 12;
let isLoading = false;

const categories = document.querySelectorAll('.category');
const sortSelect = document.querySelector('.sort-box select');

// 스크롤 이벤트 콜백 함수 분리
function onScrollLoad() {
    if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
        searchProductList();
    }
}

// 스크롤 이벤트 등록 함수
function registerScrollEvent() {
    $(window).off('scroll', onScrollLoad); // 중복 방지
    $(window).on('scroll', onScrollLoad);
}


categories.forEach(category => {
    category.addEventListener('click', () => {
        const selectedCategory = category.dataset.category;
        if (currentCategory === selectedCategory) return;
        categories.forEach(c => c.classList.remove('active'));
        category.classList.add('active');
        currentPage = 1; // Reset to first page on category change
        currentSort = 'newest'; // Reset sort to default on category change
        sortSelect.selectedIndex = 0; // Reset sort select to first option
        currentCategory = selectedCategory;
        searchProductList();
        registerScrollEvent();

    });
});
sortSelect.addEventListener('change', function () {
    const selectedOption = this.options[this.selectedIndex];
    const sortValue = selectedOption.dataset.sort;
    if (!sortValue || sortValue === currentSort) return;

    currentPage = 1; // Reset to first page on sort change
    currentSort = sortValue;
    searchProductList();
    registerScrollEvent();
});

// 최초 등록
registerScrollEvent();


searchProductList = () => {
    if(isLoading) return; // 이미 로딩 중이면 중복 요청 방지
    isLoading = true; // 로딩 상태 설정


    axios.get(`/api/product/${currentCategory}`, {
        params: {
            sort: currentSort,
            page: currentPage,
            size: currentPageSize
        }

    }).then(response => {
        if(!response.data.success.responseData) {
            isLoading = false; // 로딩 상태 해제
            return;
        }
        const lastPage = response.data.success.responseData.totalPages;
        if (lastPage === currentPage)
            $(window).off('scroll', onScrollLoad); // 스크롤 이벤트 해제

        const items = response.data.success.responseData.items;
        const productList = document.querySelector('.product-list');
        if (currentPage === 1)
            productList.innerHTML = ''; // 첫 페이지 로딩 시 기존 내용 초기화


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
          <img src="${thumbnail ? thumbnail : '/images/product/test2.png'}" alt="${productName}">
           <button type="button" class="btn-wish${wished ? ' active' : ''}"><span class="blind">좋아요</span></button>
        </div>
        <div class="product-info">
          <p class="product-name">${productName}</p>
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
            productList.appendChild(li);
        });
        currentPage++; // 다음 페이지를 위해 페이지 증가
    }).catch(error => {
        console.error('Error fetching product list:', error);
    }).finally(()=>{
        isLoading = false; // 로딩 상태 해제
    });

}
searchProductList();


document.querySelector('.product-list').addEventListener('click', function(e) {
    const btn = e.target.closest('.btn-wish');//가장가까운 부모
    if (btn) {//두번째인자는 body 세번째는 쿼리스트링포함가능
        axios.put(`/api/product/steam`,{},{
            params : {
                productId: btn.closest('li').dataset.productId
            }
        })
            .then(response => {
                console.log(response)
                btn.classList.toggle('active');
            })
            .catch(error => {
                console.error('Error updating wish status:', error);
            });
    }
});