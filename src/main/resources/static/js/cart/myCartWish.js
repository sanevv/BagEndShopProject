axios.get('/api/cart')  // 여기에 백엔드 장바구니 API 경로 입력
    .then(response => {
        const cartItems = response.data.success.responseData;
        const container = document.getElementById('cartContainer');

        cartItems.forEach(item => {
            const div = document.createElement('div');
            div.className = 'cart-item';

            div.innerHTML = `
            <img src="${item.productImage}" alt="${item.productName}">
            <div class="cart-item-details">
              <h3>${item.productName}</h3>
              <p>수량: ${item.quantity}개</p>
              <p>상품 ID: ${item.productId}</p>
            </div>
          `;

            container.appendChild(div);
        });
    })
    .catch(error => {
        console.error('장바구니 로딩 실패:', error);
    });
