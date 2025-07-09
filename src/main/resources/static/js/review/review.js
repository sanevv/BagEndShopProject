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

    if( form.reviewContents.value.trim() === "" ){
        alert("리뷰 내용을 입력해주세요.");
        form.reviewContents.focus();
        return;
    }

    if (form.rating.value == 0) {
        alert("리뷰에 대한 평점을 선택해주세요.");
        document.querySelector('.star').focus();
        return;
    }

    const formData = new FormData();
    formData.append("userId", form.userId.value);
    formData.append("productId", productId);
    formData.append("reviewContents", form.reviewContents.value);
    formData.append("rating", rating.value);
    formData.append("file", form.reviewImageFile.files[0]);

    fetch('/api/review/write', {
        method: 'POST',
        body: formData
    })
        .then(res => {
            if (!res.ok) {
                return res.json().then(error => {
                    // 백에서 fromMessage로 담은 값이 나옴
                    alert(error.message);
                    throw new Error("서버 오류 응답: " + error.message);
                });
            }

            return res.json();
        })
        .then(data => {

            alert("리뷰가 등록되었습니다!");
            location.href = `/product/detail/${productId}`;
        })
        .catch(err => {
            console.error(err);
            history.back();
        });


}

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
            alert('관리자 댓글이 등록되었습니다.');
            // 모달 닫기
            document.getElementById('reviewCommentModal').style.display = 'none';
            document.body.classList.remove('modal-open');

            // 페이지 새로고침
            location.reload();
        } else {
            alert('관리자 댓글 등록에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('관리자 댓글 등록 중 오류가 발생했습니다.');
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
                alert('관리자 댓글이 수정되었습니다.');
                // 모달 닫기
                document.getElementById('reviewCommentUpdateModal').style.display = 'none';
                document.body.classList.remove('modal-open');

                // 페이지 새로고침
                location.reload();
            } else {
                alert('관리자 댓글 수정에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('관리자 댓글 등록 중 오류가 발생했습니다.');
        });

}

// 관리자 댓글 삭제하기
reviewCommentDelete = (reviewId) => {

    if(!confirm("관리자 댓글을 삭제하시겠습니까?")) return;

    fetch('/api/comment/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            reviewId: parseInt(reviewId),
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if(!data.success){
                return alert('댓글 삭제에 실패했습니다.');
            }

            alert('관리자 댓글이 삭제 되었습니다.');
            // 페이지 새로고침
            location.reload();

        })
        .catch(error => {
            console.error('Error:', error);
            alert('관리자 댓글 삭제 중 오류가 발생했습니다.');
        });
}

const btnDeleteReview = document.querySelectorAll(".btn-review-delete");
btnDeleteReview.forEach(btn => {
    btn.addEventListener("click", reviewDelete);
})


reviewImagePreview = () => {

    const fileInput = document.getElementById("reviewImageFile");
    const previewContainer = document.getElementById("reviewImagePreview");

    //console.log("fileInput.files[0] : ", fileInput.files[0]);
    const file = fileInput.files[0];
    if (file) {

        const allowedExtensions = ["jpg", "jpeg", "png", "gif"];
        const fileName = file.name.toLowerCase();
        const fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

        if (!allowedExtensions.includes(fileExtension)) {
            alert("jpg, jpeg, png, gif 형식의 이미지 파일만 업로드할 수 있습니다.");
            fileInput.value = ""; // 잘못된 파일 선택 초기화
            return;
        }

        const reader = new FileReader();

        // 미리보기 초기화
        previewContainer.innerHTML = "";

        reader.onload = function(e) {
            const img = document.createElement("img");
            img.src = e.target.result;
            img.alt = "미리보기";
            img.style.maxWidth = "400px";
            img.style.border = "1px solid #ccc";
            img.style.marginTop = "10px";
            previewContainer.appendChild(img);
        };

        reader.readAsDataURL(file);

    }
}

if( document.querySelector("#reviewImageFile") )
    document.querySelector("#reviewImageFile").addEventListener("change", reviewImagePreview);




