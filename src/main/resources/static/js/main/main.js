window.addEventListener("DOMContentLoaded", function () {
    setTimeout(() => {
        mainBanner();
    }, 500)
});

fetch("/api/product/main")
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(res => {
        console.log("전체 응답 확인:", res);

        // res.success가 null 또는 undefined가 아닐 경우에만
        // responseData 속성을 꺼내기
        const products = res.success?.responseData;

        // 받아온 responseData 값이 배열인지 체크하기
        if (Array.isArray(products)) {
            const mainBanner = document.querySelector("#mainBanner");
            // 기존 내용 초기화
            mainBanner.innerHTML = "";
            let imgUrl = "";
            let bannerHTML = ``;
            products.forEach(item => {

                imgUrl = (item.thumbnail != null)
                    ? "url("+item.thumbnail+")"
                    : "url(/images/error/product_empty.png)";


                bannerHTML += `
                    <div class="swiper-slide">
                        <div class="banner-img" style="background-image: ${imgUrl}"></div>
                        <p class="product-name">${item.productName}</p>
                        <a class="product-link" href="/product/detail/${item.productId}"></a>
                    </div>
                `;
            });

            mainBanner.innerHTML = bannerHTML;
        } else {
            console.warn("상품 배열이 존재하지 않음.");
        }
    })
    .catch(error => {
        console.error("API 호출 실패:", error);
    });

mainBanner = () => {
    new Swiper(".main-banner", {
        slidesPerView: 3,
        spaceBetween: 30,
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
    })
}
