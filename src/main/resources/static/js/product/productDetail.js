let productId;

document.addEventListener('DOMContentLoaded', () => {

    // productId 값 가져오기
    productId = document.querySelector('[name="productId"]').value;

    // 금액 초기화
    priceInit();

    // 초기 리뷰리스트 보여주기
    reviewListLoad(productId, 1);

    document.addEventListener('click', function(e) {

        const btnReviewComment = e.target.closest('.btn-review-comment');
        //const btnSubmit = e.target.closest('#btnCommentSubmit');
        // 관리자 댓글 버튼 클릭한 경우
        if (btnReviewComment) {
            const reviewId = btnReviewComment.dataset.reviewid;
            const userId = btnReviewComment.dataset.userid;

            //console.log('관리자 댓글 버튼 클릭:', btnReviewComment);
            //console.log('data-reviewId 값:', reviewId);

            const btnCommentSubmit = document.querySelector('#btnCommentSubmit');
            if (btnCommentSubmit) {
                btnCommentSubmit.dataset.reviewid = reviewId;
                btnCommentSubmit.dataset.userid = userId;
                //console.log('가져왔나 comment 설정:', btnCommentSubmit.dataset.comment);
            }
        }

        // 댓글 제출 버튼 클릭
        if (e.target.closest('#btnCommentSubmit')) {
            const reviewId = e.target.dataset.reviewid;
            const userId = e.target.dataset.userid;
            const commentContents = document.querySelector('#commentContents').value.trim();

            if (!commentContents) {
                alert('댓글 내용을 입력해주세요.');
                return;
            }

            //console.log("댓글 내용은요 : ", commentContents);

            reviewCommentSubmit(userId, reviewId, commentContents);
        }



        const btnReviewCommentUpdate = e.target.closest('.btn-review-comment-update');
        // 관리자 댓글 수정하기 버튼 클릭한 경우
        if (btnReviewCommentUpdate) {
            const reviewId = btnReviewCommentUpdate.dataset.reviewid;
            const userId = btnReviewCommentUpdate.dataset.userid;
            const commentId = btnReviewCommentUpdate.dataset.commentid;

            console.log('관리자 댓글 수정하기 버튼 클릭:', btnReviewCommentUpdate);
            console.log('data-reviewId 값:', reviewId);

            const btnCommentUpdate = document.querySelector('#btnCommentUpdate');
            if (btnCommentUpdate) {
                btnCommentUpdate.dataset.reviewid = reviewId;
                btnCommentUpdate.dataset.userid = userId;
                btnCommentUpdate.dataset.commentid = commentId;
            }
        }

        // 댓글 수정하기 버튼 클릭
        if (e.target.closest('#btnCommentUpdate')) {
            const reviewId = e.target.dataset.reviewid;
            const userId = e.target.dataset.userid;
            const commentId = e.target.dataset.commentid;
            const commentContents = document.querySelector('#commentUpdateContents').value.trim();

            if (!commentContents) {
                alert('수정할 댓글 내용을 입력해주세요.');
                return;
            }

            //console.log("댓글 내용은요 : ", commentContents);

            reviewCommentUpdate(userId, reviewId, commentId, commentContents);
        }


    });

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
        console.log(review);

        const rating = Number(review.rating);
        const halfRating = rating / 2;
        const displayRating = (halfRating % 1 === 0) ? halfRating : halfRating.toFixed(1);

        const hasComment = (!review.comment) ? 0 : 1;

        reviewListHTML += `
                    <li class="review-item">
                        <div class="box">
                            <p class="title">${review.reviewContents}</p>
                            <div class="info">
                                <span class="name">작성자 ${review.userName}</span>
                                <span class="date">${review.createdAt}</span>
                                <span class="rating" data-rating="${review.rating}">별이 ${displayRating}개!!</span>
                                <span class="comment">달린 코멘트 : ${hasComment} 개</span> `;
                                if(hasComment === 1){
                                    reviewListHTML += `
                                        <div class="admin-comment"> 
                                            <p class="title">관리자 답변</p>
                                            <p class="text">${review.commentContents}</p>
                                        </div> 
                                    `;
                                }
reviewListHTML += `    </div> `;


        // 로그인한 userid와 리뷰 작성한 userid가 같으면 보여줌
        if(review.loginReviewUser) {

            reviewListHTML += `<div class="review-buttons">
                                    <button type="button" class="btn btn-review-update" onclick="isLoginCheck('update', ${review.reviewId})">수정하기</button>
                                    <button type="button" class="btn btn-review-delete" onclick="reviewDelete(${review.reviewId}, ${review.productId})">삭제하기</button>
                                </div>
                               `;
        }

        // 로그인한 사람이 관리자면 onclick="reviewAdminComment(${review.reviewId})"
        if(loginUserRoleId === 1){
            reviewListHTML += `<div class="review-buttons">`;
                                switch (hasComment) {
                                    case 0:
                                        reviewListHTML += `<button type="button" class="btn btn-review-comment black" data-toggle="modal" data-target="#reviewCommentModal" data-userid="${review.userId}" data-reviewid="${review.reviewId}">관리자 댓글 작성하기</button>`;
                                        break;
                                    case 1:
                                        reviewListHTML += `<button type="button" class="btn btn-review-comment-update black" data-toggle="modal" data-target="#reviewCommentUpdateModal" data-userid="${review.userId}" data-reviewid="${review.reviewId}" data-commentid="${review.reviewCommentId}">관리자 댓글 수정하기</button>`;
                                        reviewListHTML += `<button type="button" class="btn btn-review-comment-delete red" onclick="reviewCommentDelete(${review.reviewId})">관리자 댓글 삭제하기</button>`;
                                        break;
                                }
            reviewListHTML += `    <button type="button" class="btn btn-review-delete" onclick="reviewDelete(${review.reviewId}, ${review.productId})">해당 리뷰 삭제하기</button> 
                                </div> `;

        }

        reviewListHTML += `
                                <a href="/review/detail/${productId}/${review.reviewId}" class="btn-review-detail"></a>
                            </div>
                        <div class="image">
                            <img src="${review.productImagePath}" alt="상품 대표이미지" onerror="this.src='/images/error/zz.png' " />
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

        if (!confirm("리뷰를 "+msg+"하기 위해선 \n로그인이 필요합니다.\n로그인 하러 가시겠습니까?")) return;
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



// 바로구매하기 버튼 클릭 이벤트
document.querySelector('#btnBuy').addEventListener('click', () => {
    requestOrderProducts([{ productId: productId, quantity: inpQuantity.value}]);
});
