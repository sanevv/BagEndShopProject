
document.addEventListener('DOMContentLoaded', () => {
    // 금액 초기화
    priceInit();

});


const productId = document.querySelector('[name="productId"]').value;

// 리뷰리스트 불러오기
fetch(`/api/review/list?productId=${productId}`)
    .then(response => response.json())
    .then(reviews => {  // 직접 배열 받기

        console.log(reviews);

        if (Array.isArray(reviews)) {

            let reviewListHTML = ``;

            if (reviews.length > 0) {

                reviews.forEach(review => {

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
                                </div>
                                `;

                    // 로그인한 userid와 리뷰 작성한 userid가 같으면 보여줌
                    if(review.loginReviewUser){

                    reviewListHTML += ` <div class="review-buttons">
                                            <button type="button" class="btn btn-review-update" onclick="isLoginCheck('update', ${review.reviewId})">수정하기</button>
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
                });
            } else {
                reviewListHTML += `<li class="review-empty">등록된 후기가 없어요.... <br> 인기가 없네요</li>`;
            }

            document.querySelector("#reviewList").innerHTML = reviewListHTML;


        }
    })
    .catch(error => console.error("API 호출 실패:", error));



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
        alert("리뷰를 "+msg+"하기 위해선 로그인이 필요합니다.");
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


