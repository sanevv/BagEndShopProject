let productId;

document.addEventListener('DOMContentLoaded', () => {

    // productId 값 가져오기
    productId = document.querySelector('[name="productId"]').value;

    // 금액 초기화
    priceInit();

    // 초기 리뷰리스트 보여주기
    reviewListLoad(productId, 1);

});



// 리뷰리스트 보여주기
reviewListLoad = (productId, page) => {
    fetch(`/api/review/list?productId=${productId}&page=${page}&sizePerPage=5`)
        .then(response => response.json())
        .then(data => {

            reviewListCall(data.reviewList, data.loginUserRoleId);
            reviewPaginationCall(data.currentPage, data.totalPages, productId);

        })
        .catch(error => console.error("API 호출 실패:", error));
}

// 리뷰리스트 그려주기
reviewListCall = (reviewList, loginUserRoleId) => {

    const reviewListMain = document.querySelector('#reviewList');


    if (!reviewList || reviewList.length === 0) {
        reviewListMain.innerHTML = `<li class="review-empty">등록된 후기가 없어요.... <br> 인기가 없네요</li>`;
        return;
    }

    let reviewListHTML = ``;

    reviewList.forEach(review => {
        //console.log(review);

        const rating = Number(review.rating);
        const halfRating = rating / 2;
        const displayRating = (halfRating % 1 === 0) ? halfRating : halfRating.toFixed(1);

        reviewListHTML += `
                    <li class="review-item">
                        <div class="box">
                            <p class="title">${review.reviewContents}</p>
                            <div class="info">
                                <span class="name">작성자 ${review.userName}</span>
                                <span class="date">${review.createdAt}</span>
                                <span class="rating" data-rating="${review.rating}">별이 ${displayRating}개!!</span>
                                <span class="comment">달린 코멘트 개수 표시할 예정</span>
                            </div>
                            `;

        // 로그인한 userid와 리뷰 작성한 userid가 같으면 보여줌
        if(review.loginReviewUser) {

            reviewListHTML += `<div class="review-buttons">
                                    <button type="button" class="btn btn-review-update" onclick="isLoginCheck('update', ${review.reviewId})">수정하기</button>
                                    <button type="button" class="btn btn-review-delete" onclick="reviewDelete(${review.reviewId}, ${review.productId})">삭제하기</button>
                                </div>
                               `;
        }

        // 로그인한 사람이 관리자면
        if(loginUserRoleId === 1){
        reviewListHTML += `<div class="review-buttons">
                                <button type="button" class="btn btn-review-comment black" onclick="reviewAdminComment(${review.reviewId})">관리자 댓글쓰기</button>
                                <button type="button" class="btn btn-review-delete" onclick="reviewDelete(${review.reviewId}, ${review.productId})">삭제하기</button>
                            </div>
                           `;
        }

        reviewListHTML += `
                                <a href="/review/detail/${productId}/${review.reviewId}" class="btn-review-detail"></a>
                            </div>
                        <div class="image">
                            <img src="${review.productImagePath}" alt="상품 대표이미지" />
                        </div>
                    </li>
                `;


        reviewListMain.innerHTML = reviewListHTML;

    })

}

// 리뷰리스트 페이지네이션 보여주기
reviewPaginationCall = (currentPage, totalPages, productId) => {

    const reviewListPagination = document.querySelector('#pageBar');

    if(!reviewListPagination) return;
    if (totalPages <= 1) {
        reviewListPagination.innerHTML = '';
        return;
    }

    let reviewListPaginationHTML = ``;

    // 이전
    if (currentPage > 1) {
        reviewListPaginationHTML += `<button class="page-btn" data-page="${currentPage - 1}">‹</button>`;
    }

    // 페이지 번호들
    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || Math.abs(i - currentPage) <= 1) {
            const active = i === currentPage ? 'active' : '';
            reviewListPaginationHTML += `<button class="page-btn ${active}" data-page="${i}">${i}</button>`;
        }
    }

    // 다음
    if (currentPage < totalPages) {
        reviewListPaginationHTML += `<button class="page-btn" data-page="${currentPage + 1}">›</button>`;
    }

    reviewListPagination.innerHTML = reviewListPaginationHTML;

    // 클릭 이벤트
    reviewListPagination.onclick = (e) => {
        if (e.target.dataset.page) {
            reviewListLoad(productId, parseInt(e.target.dataset.page));
        }
    };

}


window.isLoginCheck = (val, reviewId) => {

    // case write : 작성
    // case update : 수정
    let msg = "";
    let url = "";
    switch (val){
        case "write":
            msg = "작성";
            url = `/review/${val}?productId=${productId}`;
            break;
        case "update":
            msg = "수정";
            url = `/review/${val}?productId=${productId}&reviewId=${reviewId}`;
            break;
    }
    if(!isLogin) {

        if (!confirm("리뷰를 "+msg+"하기 위해선 \n 로그인이 필요합니다.")) return;
        location.href = '/test/login.up';
        return;
    }

    location.href = url;
}

const inpQuantity = document.querySelector('[name="quantity"]');
const priceElement = document.querySelector('.price');
const totalAmountElement = document.querySelector('.total-amount');

priceInit = () => {

    const count = Math.max(0, Number(inpQuantity.value) || 0); // 음수 방지, NaN일 경우 0
    const priceText = priceElement.textContent.replace(/[^0-9]/g, ''); // 숫자만 추출
    const price = Number(priceText) || 0;

    //console.log('입력 즉시 값 변경됨:', count);
    //console.log('price:', price);

    const totalPrice = count * price;
    //console.log('totalPrice:', totalPrice);

    totalAmountElement.textContent = totalPrice.toLocaleString() + "원";
}

addCart = () => {

    const form = document.addCartForm;

    const addCartData = {
        productId: productId,
        quantity: form.quantity.value,
    };


    fetch('/api/cart', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(addCartData)
    })
    .then(async response => {
        const contentType = response.headers.get('content-type');

        if (contentType && contentType.includes('application/json')) {
            // JSON 응답일 경우 처리
            const data = await response.json();

            if (response.ok) {
                console.log('성공 응답:', data);
                if (confirm('해당 상품이 장바구니에 담겼습니다! 이동하시겠습니까?')) {
                    location.href = '/cart';
                }
            } else {
                console.warn('에러 응답:', data);
                alert(data.message || '장바구니 추가 실패');
            }
        } else {
            // 로그인 페이지 리디렉션 시킥
            alert('로그인 후 장바구니에 상품을 추가해주세요.');
            location.href = '/test/login.up';
        }
    })
    .catch(error => {
        console.error('요청 중 오류 발생:', error);
        alert('서버 또는 네트워크 오류가 발생했습니다.');
    });
}

const btnAddCart = document.querySelector("#btnAddCart");
btnAddCart.addEventListener("click", addCart);

const btnMinus = document.querySelector('.btn-min');
const btnPlus = document.querySelector('.btn-plus');

// - 버튼 클릭 이벤트
btnMinus.addEventListener('click', () => {
    let currentValue = parseInt(inpQuantity.value) || 1;
    let min = parseInt(inpQuantity.min) || 1;

    if (currentValue > min) {
        inpQuantity.value = currentValue - 1;
        priceInit();
    }
});

// + 버튼 클릭 이벤트
btnPlus.addEventListener('click', () => {
    let currentValue = parseInt(inpQuantity.value) || 1;
    let max = parseInt(inpQuantity.max) || 100;

    if (currentValue < max) {
        inpQuantity.value = currentValue + 1;
        priceInit();
    }
});



document.querySelector('#btnBuy').addEventListener('click', () => {
    requestOrderProducts([{ productId: productId, quantity: inpQuantity.value}]);
});


