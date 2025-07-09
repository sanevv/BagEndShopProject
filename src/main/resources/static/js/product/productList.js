let currentCategory = !category ? 'all' : category;// category 는 ALL("전체"),MESSENGER("메신저"),CROSS("크로스백"),BACKPACK("백팩"); 참고
let currentSearchKeyword = searchKeyword; // 검색어
let currentSort = 'newest';
let currentPage = 1;
const currentPageSize = 12;
let isLoading = false;

const categories = document.querySelectorAll('.category');
const sortSelect = document.querySelector('.sort-box select');
const searchKeywordDiv = document.querySelector('#searchKeywordDiv');
const closeButton = document.querySelector('#closeButton');
const searchKeywordText = document.querySelector('#keywordStrong');

//옵저버
const sentinel = document.querySelector('#scrollSentinel');

const observer = new IntersectionObserver(async (entries) => {
    const entry = entries[0];
    if (entry.isIntersecting && !isLoading) {
        await searchProductList();
    }
}, {
    rootMargin: '100px', // 미리 로딩할 거리
    threshold: 0.1
});
//옵저버

if (searchKeyword) {
    searchKeywordDiv.style.display = 'flex'; // 검색어가 있을 때만 검색어 표시
    searchKeywordText.innerText = currentSearchKeyword; // 검색어 표시
}





closeButton.addEventListener('click', () => {
    currentSearchKeyword = ''; // 검색어 초기화
    searchKeywordText.innerText = ''; // 검색어 표시 초기화
    searchKeywordDiv.style.display = 'none'; // 검색어 숨김
    currentPage = 1; // 페이지 초기화
    searchProductList(); // 검색어 초기화 후 상품 목록 재조회
    observer.observe(sentinel);
})

productPageMovement = (searchKeyword) => {
    if (!searchKeyword) {
        alert("검색어를 입력해주세요.");
        return;
    }
    currentSearchKeyword = searchKeyword; // 검색어 설정
    currentPage = 1; // 페이지 초기화
    searchProductList(); // 상품 목록 조회
    searchKeywordDiv.style.display = 'flex'; // 검색어가 있을 때만 검색어 표시
    searchKeywordText.innerText = currentSearchKeyword; // 검색어 표시

    observer.observe(sentinel);
}


// 스크롤 이벤트 콜백 함수 분리
let scrollTimeout;
// function onScrollLoad() {
//     clearTimeout(scrollTimeout);
//     scrollTimeout = setTimeout(() => {
//         const scrollBottom = $(window).scrollTop() + $(window).height();
//         const documentHeight = $(document).height();
//
//         if (scrollBottom >= documentHeight - 100) {
//             searchProductList();
//         }
//     }, 100); // 100ms 내에 스크롤 멈춘 경우만 실행
// }

// 스크롤 이벤트 등록 함수
// function registerScrollEvent() {
//     $(window).off('scroll', onScrollLoad); // 중복 방지
//     $(window).on('scroll', onScrollLoad);
// }

//카테고리 초기 액티브 등록
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
        currentPage = 1; // Reset to first page on category change
        currentSort = 'newest'; // Reset sort to default on category change
        sortSelect.selectedIndex = 0; // Reset sort select to first option
        currentCategory = selectedCategory;
        searchProductList();
        observer.observe(sentinel);

    });
});
sortSelect.addEventListener('change', function () {
    const selectedOption = this.options[this.selectedIndex];
    const sortValue = selectedOption.dataset.sort;
    if (!sortValue || sortValue === currentSort) return;

    currentPage = 1; // Reset to first page on sort change
    currentSort = sortValue;
    searchProductList();
    observer.observe(sentinel);
});





searchProductList = async () => {
    if (isLoading) return; // 이미 로딩 중이면 중복 요청 방지
    isLoading = true; // 로딩 상태 설정


    axios.get(`/api/product/${currentCategory}`, {
        params: {
            sort: currentSort,
            page: currentPage,
            size: currentPageSize,
            search: currentSearchKeyword
        }

    }).then(response => {
        if (!response.data.success.responseData) {
            isLoading = false; // 로딩 상태 해제
            return;
        }
        const lastPage = response.data.success.responseData.totalPages;
        if (lastPage === currentPage)
            observer.unobserve(sentinel); // 더 이상 감지하지 않음

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
            <div class="product-back-img show-detail" style="background-image: url(${thumbnail ? thumbnail : '/images/error/zz.png'})"></div>
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
                el.addEventListener('click', (e) => {
                    e.stopPropagation(); // 이벤트 전파 방지 부모의 이벤트리스너는 실행되지않음
                    //클릭시 이동전 현재상태저장
                    // buildSafeUrl();
                    location.href = `/product/detail/${productId}`;
                });
            })
            productList.appendChild(li);
        });
        currentPage++; // 다음 페이지를 위해 페이지 증가
    }).catch(error => {
        console.error('Error fetching product list:', error);
    }).finally(() => {
        isLoading = false; // 로딩 상태 해제
    });

}


//todo:저장할상태
function buildSafeUrl() {
    // 저장할 상태
    const listState = {
        scrollY: window.scrollY,
        category: currentCategory,
        search: currentSearchKeyword,
        sort: currentSort,
        page: currentPage,
    };

// 상태를 세션스토리지에 저장
    sessionStorage.setItem('productListState', JSON.stringify(listState));
}


//todo: 페이지 로드시 확인
const savedState = sessionStorage.getItem('productListState');
if (savedState) {
    const {
        scrollY,
        category,
        search,
        sort,
        page
    } = JSON.parse(savedState);
    // 저장된 상태를 복원
    currentCategory = category;
    currentSearchKeyword = search;
    currentSort = sort;
    currentPage = page;

    // 카테고리 active 처리
    categories.forEach(c => {
        if (c.dataset.category === currentCategory) {
            c.classList.add('active');
        } else {
            c.classList.remove('active');
        }
    });

    // 정렬 select 처리
    for (let i = 0; i < sortSelect.options.length; i++) {
        if (sortSelect.options[i].dataset.sort === sort) {
            sortSelect.selectedIndex = i;
            break;
        }
    }

    // 검색어 표시
    if (search) {
        searchKeywordDiv.style.display = 'flex';
        searchKeywordText.innerText = search;
    }

    // 상품 목록 불러오기 반복실행
    (async () => {
        await pageLoadRepeat();
        // 스크롤이벤트등록
        observer.observe(sentinel);
        // 복원 후 scroll 위치 이동
        setTimeout(() => {
            window.scrollTo(0, scrollY);
        }, 200);

        // 상태 제거 (뒤로가기로 다시 와도 복원 반복되지 않게)
        sessionStorage.removeItem('productListState');
    })();

}
else {
    // 초기 진입일 경우 기본 상품 리스트 호출
    (async () => {
        await searchProductList();
        // 최초 등록
        observer.observe(sentinel);
    })();
}

async function pageLoadRepeat() {
    const savedPage = currentPage;
    currentPage = 1; // 페이지 초기화
    for (let i = 1; i <= savedPage; i++) {
        await searchProductList();
    }
}


document.querySelector('.product-list').addEventListener('click', function (e) {
    const btn = e.target.closest('.btn-wish');//가장가까운 부모
    if (btn) {//두번째인자는 body 세번째는 쿼리스트링포함가능
        axios.put(`/api/product/steam`, {}, {
            params: {
                productId: btn.closest('li').dataset.productId
            }
        })
            .then(response => {
                console.log(response)
                btn.classList.toggle('active');
            })
            .catch(error => {
                console.error('Error updating wish status:', error);
                if (error.response && error.response.status === 400)
                    alert(error.response.data.message);
            });
    }
});




