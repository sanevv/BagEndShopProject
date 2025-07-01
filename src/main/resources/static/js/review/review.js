const starBox = document.querySelectorAll(".star-box");
const rating = document.querySelector('[name="rating"]');

starBox.forEach((star, idx) => {
    star.addEventListener("click", () => {
        rating.value = idx+1;
    });
});

// 리뷰 작성 폼에서 데이터를 수집해서 fetch로 서버에 POST 전송하기
reviewSubmit = () => {

    const params = new URLSearchParams(window.location.search);
    const productId = params.get("productId");

    const form2 = document.forms['reviewWriteForm']; // 폼 요소

    const formData = new FormData();
    formData.append("userId", form2.userId.value);
    formData.append("productId", productId);
    formData.append("reviewContents", form2.reviewContents.value);
    formData.append("rating", rating.value);
    formData.append("file", form2.reviewImageFile.files[0]);

    const form = document.reviewWriteForm;
    const reviewData = {
        userId: form.userId.value,
        productId: productId,
        reviewContents: form.reviewContents.value,
        rating: rating.value,
        reviewImagePath: form.reviewImageFile.value
    };

    fetch('/api/review/write', {
        method: 'POST',
        body: formData // Content-Type 설정 → 자동 처리됨
    })
    .then(res => res.json())
    .then(data => {
        console.log("리뷰 등록 성공", data);
        alert('리뷰가 등록되었습니다!');
        location.href = `/product/detail/${productId}`;
    });

    // fetch('/api/review/write', {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify(reviewData)
    //
    // })
    // .then(response => response.json())
    // .then(data => {
    //     console.log(data);
    //     alert('리뷰가 등록되었습니다!');
    //     location.href = `/product/detail/${productId}`;
    //     // 페이지 새로고침 없이 리뷰 목록 업데이트
    // });

}

reviewDelete = (reviewId, productId) => {

    if (!confirm('정말 이 리뷰를 삭제하시겠습니까?')) return;

    fetch('/api/review/delete', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ reviewId: reviewId })
    })
    .then(async (response) => {
        console.log("response.status : ", response.status);
        const msg = await response.text();

        switch (response.status) {
            case 200:
                alert('리뷰가 삭제되었습니다.');
                location.href = `/product/detail/${productId}`;
                break;
            case 401:
                alert('리뷰 삭제에 실패했습니다.\n이유: ' + msg);
                location.href = `/test/login.up`;
                break;
            case 403:
                alert('본인 리뷰만 삭제할 수 있습니다.');
                break;
            default:
                alert('리뷰 삭제 실패: ' + msg);
                break;
        }
    })
    .catch(error => {
        console.error('삭제 중 오류 발생:', error.message);
        alert('서버 또는 네트워크 오류입니다.');
    });

}


const btnDeleteReview = document.querySelectorAll(".btn-review-delete");
btnDeleteReview.forEach(btn => {
    btn.addEventListener("click", reviewDelete);
})
//
// const btnUpdateReview = document.querySelector("#btnUpdateReview");
// btnUpdateReview.addEventListener("click", reviewUpdate);






