
axios.get("/api/product/main")
    .then(response =>{
        console.log(response.data);
        // res.success가 null 또는 undefined가 아닐 경우에만
        // responseData 속성을 꺼내기
        const products = response.success?.responseData;

        // 받아온 responseData 값이 배열인지 체크하기
        if (Array.isArray(products)) {
            const ul = document.getElementById("product-list");
            // 기존 내용 초기화
            ul.innerHTML = "";

            products.forEach(item => {
                const li = document.createElement("li");
                // 이미지 생성
                const img = document.createElement("img");
                img.src = item.thumbnail;
                img.alt = item.productName;

                const productName = document.createElement("p");
                productName.textContent = item.productName;

                const productId = document.createElement("p")
                productId.textContent = item.productId;

                li.appendChild(img);
                li.appendChild(productName);
                li.appendChild(productId);

                ul.appendChild(li);
            });
        }
    })
    .catch(error => {
        console.error("API 호출 실패:", error);
    })
//
// fetch("/api/product/main")
//     .then(response => {
//         if (!response.ok) {
//             throw new Error(`HTTP error! status: ${response.status}`);
//         }
//         return response.json();
//     })
//     .then(res => {
//         console.log("전체 응답 확인:", res);
//
//         // res.success가 null 또는 undefined가 아닐 경우에만
//         // responseData 속성을 꺼내기
//         const products = res.success?.responseData;
//
//         // 받아온 responseData 값이 배열인지 체크하기
//         if (Array.isArray(products)) {
//             const ul = document.getElementById("product-list");
//             // 기존 내용 초기화
//             ul.innerHTML = "";
//
//             products.forEach(item => {
//                 const li = document.createElement("li");
//                 // 이미지 생성
//                 const img = document.createElement("img");
//                 img.src = item.thumbnail;
//                 img.alt = item.productName;
//
//                 const productName = document.createElement("p");
//                 productName.textContent = item.productName;
//
//                 const productId = document.createElement("p")
//                 productId.textContent = item.productId;
//
//                 li.appendChild(img);
//                 li.appendChild(productName);
//                 li.appendChild(productId);
//
//                 ul.appendChild(li);
//             });
//         } else {
//             console.warn("상품 배열이 존재하지 않음.");
//         }
//     })
//     .catch(error => {
//         console.error("API 호출 실패:", error);
//     });