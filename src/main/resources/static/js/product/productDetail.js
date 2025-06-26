const productId = document.querySelector('[name="productId"]').value;
const userName = document.querySelector('[name="userName"]').value;

// 리뷰리스트 불러오기
fetch(`/api/review/list?productId=${productId}`)
    .then(response => response.json())
    .then(reviews => {  // 직접 배열 받기
        if (Array.isArray(reviews)) {

            let reviewListHTML = ``;

            if(reviews.length > 0) {

                reviews.forEach(review => {
                    reviewListHTML += `
                        <li class="review-item">
                            <div class="box">
                                <p class="title">${review.reviewContents}</p>
                                <div class="info">
                                    <span class="name">사용자${userName}</span>
                                    <span class="date">${review.createdAt}</span>
                                    <span class="rating">별이 ${review.rating}개!!</span>
                                </div>
                            </div>
                            <div class="image">
                                <img src="${review.productImagePath}" alt="상품 대표이미지" />
                            </div>
                        </li>
                    `;
                });
            }
            else {
                reviewListHTML += `<li class="review-empty">등록된 후기가 없어요.... <br> 인기가 없네요</li>`;
            }

            document.querySelector("#reviewList").innerHTML = reviewListHTML;
        }
    })
    .catch(error => console.error("API 호출 실패:", error));