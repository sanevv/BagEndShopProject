

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

    const form = document.reviewWriteForm;
    const reviewData = {
        userId: form.userId.value,
        productId: productId,
        reviewContents: form.reviewContents.value,
        rating: rating.value
    };

    fetch('/api/review/write', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reviewData)
    })
    .then(response => response.json())
    .then(data => {
        alert('리뷰가 등록되었습니다!');
        location.href = `/product/detail/${productId}`;
        // 페이지 새로고침 없이 리뷰 목록 업데이트
    });

}

const btnAddReview = document.querySelector("#btnAddReview");
btnAddReview.addEventListener("click", reviewSubmit);







