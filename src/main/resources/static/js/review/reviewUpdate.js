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


    const form = document.forms['reviewUpdateForm']; // 폼 요소
    const formData = new FormData();
    formData.append("userId", form.userId.value);
    formData.append("reviewId", form.reviewId.value);
    formData.append("productId", productId);
    formData.append("reviewContents", form.reviewContents.value);
    formData.append("rating", rating.value);
    formData.append("file", form.reviewImageFile.files[0]);

    fetch('/api/review/update', {
        method: 'POST',
        body: formData // Content-Type 설정 → 자동 처리됨
    })
    .then(res => res.json())
    .then(data => {
        console.log("리뷰 수정 성공", data);
        alert('리뷰가 정상적으로 수정됐습니다!!');
        location.href = `/product/detail/${productId}`;
    })
    .catch(error => {
        //console.log(error);

        console.error('삭제 중 오류 발생:', error.message);
        //alert('서버 또는 네트워크 오류입니다.');
        alert(error.message);
    });
    // fetch('/api/review/update', {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify(reviewData)
    // })
    //     .then(async (response) => {
    //         console.log("response.status : ", response.status);
    //         const msg = await response.text();
    //
    //         switch (response.status) {
    //             case 200:
    //                 alert('리뷰가 수정되었습니다.');
    //                 location.href = `/product/detail/${productId}`;
    //                 break;
    //             case 401:
    //                 alert('리뷰 수정에 실패했습니다.\n이유: ' + msg);
    //                 location.href = `/test/login.up`;
    //                 break;
    //             case 403:
    //                 alert('본인 리뷰만 수정할 수 있습니다.');
    //                 location.href = `/product/detail/${productId}`;
    //                 break;
    //             default:
    //                 alert('리뷰 수정 실패: ' + msg);
    //                 break;
    //         }
    //     })
    //     .catch(error => {
    //         console.error('삭제 중 오류 발생:', error.message);
    //         alert('서버 또는 네트워크 오류입니다.');
    //     });

}
