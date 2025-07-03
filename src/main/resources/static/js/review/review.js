const starBox = document.querySelectorAll(".star-box");
const rating = document.querySelector('[name="rating"]');

starBox.forEach((star, idx) => {
    star.addEventListener("click", () => {
        rating.value = idx+1;
    });
});

// 리뷰 작성 폼에서 데이터를 수집해서 fetch로 서버에 POST 전송하기
reviewSubmit = () => {

    const form = document.forms['reviewWriteForm'];
    const productId = new URLSearchParams(window.location.search).get("productId");

    const formData = new FormData();
    formData.append("userId", form.userId.value);
    formData.append("productId", productId);
    formData.append("reviewContents", form.reviewContents.value);
    formData.append("rating", rating.value);
    formData.append("file", form.reviewImageFile.files[0]);

    console.log("formData : ", form.reviewImageFile.files[0]);

    fetch('/api/review/write', {
        method: 'POST',
        body: formData // Content-Type 자동 설정 (multipart/form-data)
    })
    .then(res => res.json())
    .then(data => {
        console.log("리뷰 등록 성공", data);
        alert('리뷰가 등록되었습니다!');
        location.href = `/product/detail/${productId}`;
    })
    .catch(err => {
        console.error("리뷰 등록 실패", err);
        alert('리뷰 등록 중 오류가 발생했습니다.');
    });

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

// 관리자가 댓글쓰는거
reviewCommentSubmit = (userId, reviewId, commentContents) => {
    fetch('/api/comment/write', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: parseInt(userId),
            reviewId: parseInt(reviewId),
            commentContents: commentContents
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        if (data.success) {
            alert('댓글이 등록되었습니다.');
            // 모달 닫기
            document.getElementById('reviewCommentModal').style.display = 'none';
            document.body.classList.remove('modal-open');

            // 페이지 새로고침
            location.reload();
        } else {
            alert('댓글 등록에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('댓글 등록 중 오류가 발생했습니다.');
    });

}

// 관리자 댓글 수정하기
reviewCommentUpdate = (userId, reviewId, reviewCommentId, commentContents) => {
    fetch('/api/comment/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: parseInt(userId),
            reviewId: parseInt(reviewId),
            reviewCommentId: parseInt(reviewCommentId),
            commentContents: commentContents
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.success) {
                alert('댓글이 수정되었습니다.');
                // 모달 닫기
                document.getElementById('reviewCommentUpdateModal').style.display = 'none';
                document.body.classList.remove('modal-open');

                // 페이지 새로고침
                location.reload();
            } else {
                alert('댓글 수정에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('댓글 등록 중 오류가 발생했습니다.');
        });

}

const btnDeleteReview = document.querySelectorAll(".btn-review-delete");
btnDeleteReview.forEach(btn => {
    btn.addEventListener("click", reviewDelete);
})







