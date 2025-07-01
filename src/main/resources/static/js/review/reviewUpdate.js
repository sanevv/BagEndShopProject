const starBox = document.querySelectorAll(".star-box");
const rating = document.querySelector('[name="rating"]');


starBox.forEach((star, idx) => {
    star.addEventListener("click", () => {
        rating.value = idx+1;
    });
});

// 리뷰 수정하기
reviewUpdate = () => {

    const params = new URLSearchParams(window.location.search);
    const productId = params.get("productId");

    if (!confirm('정말 이 리뷰를 수정하시겠습니까?')) return;


    const form = document.reviewUpdateForm;
    const reviewData = {
        reviewId: form.reviewId.value,
        userId: form.userId.value,
        productId: productId,
        reviewContents: form.reviewContents.value,
        rating: rating.value
    };

    fetch('/api/review/update', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reviewData)
    })
        .then(async (response) => {
            console.log("response.status : ", response.status);
            const msg = await response.text();

            switch (response.status) {
                case 200:
                    alert('리뷰가 수정되었습니다.');
                    location.href = `/product/detail/${productId}`;
                    break;
                case 401:
                    alert('리뷰 수정에 실패했습니다.\n이유: ' + msg);
                    location.href = `/test/login.up`;
                    break;
                case 403:
                    alert('본인 리뷰만 수정할 수 있습니다.');
                    location.href = `/product/detail/${productId}`;
                    break;
                default:
                    alert('리뷰 수정 실패: ' + msg);
                    break;
            }
        })
        .catch(error => {
            console.error('삭제 중 오류 발생:', error.message);
            alert('서버 또는 네트워크 오류입니다.');
        });

}
