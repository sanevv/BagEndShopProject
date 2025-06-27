const itemBody = document.getElementById('itemBody');
const paymentBody = document.getElementById('paymentBody');

getMyCartProducts = () => {
    axios.get('/api/cart')
        .then(response => {
            console.log(response.data.success.responseData);
            const cartProducts = response.data.success.responseData;
            if(!cartProducts || cartProducts.length === 0) {
                itemBody.innerHTML = '<tr><td colspan="5" class="text-center">장바구니에 담긴 상품이 없습니다.</td></tr>';
                return;
            }
            showMyCartProducts(cartProducts)
        })
        .catch(error => {
            console.error('Error fetching cart products:', error);
        });
}
showMyCartProducts = (cartProducts) => {
    let totalPrice = 0;
    let totalQuantity = 0;
    let priceAfterDiscount = 0;
    itemBody.innerHTML = cartProducts.map(product => {
        totalPrice += product.price * product.quantity;
        totalQuantity += product.quantity;
        priceAfterDiscount += Math.round(product.price * product.quantity * (1 - (product.discountRate || 0)));
        return `
        <div class="cart-item position-relative d-flex align-items-center" data-product-id="${product.productId}" data-product-cart-id="${product.productCartId}">
            <input type="checkbox" class="me-3" checked>
            <div class="me-4 item-image detail-btn">
                <img src="${product.productImage}" alt="제품">
            </div>
            <div class="flex-grow-1">
                <h5 class="mb-1 detail-btn">${product.productName}</h5>
                <p class="text-muted mb-1">${product.price ? product.price.toLocaleString() + '원' : ''}</p>
                <div class="d-flex align-items-center justify-content-end mb-2 item-qty">
                    <button class="btn btn-outline-secondary btn-sm me-1 minus-btn">-</button>
                    <input type="text" class="form-control form-control-sm me-1" data-original-value="${product.quantity}" value="${product.quantity}" readonly>
                    <button class="btn btn-outline-secondary btn-sm plus-btn me-2">+</button>
                    <button class="btn btn-primary btn-sm update-qty-btn" style="font-size: 13px" disabled>수량변경</button>
                </div>
                <div class="d-flex align-items-center justify-content-end mb-2">
                    <button class="btn btn-outline-dark btn-sm me-2 detail-btn">상세보기</button>
                    <button class="btn btn-black btn-sm order-btn">주문하기</button>
                </div>
            </div>
            <span class="item-remove">×</span>
        </div>
    `
    }).join('');
    showMyCartPaymentBody(totalPrice, totalQuantity, priceAfterDiscount);
}
showMyCartPaymentBody = (totalPrice, totalQuantity, priceAfterDiscount) => {
    paymentBody.innerHTML = `
        <div class="d-flex justify-content-between">
             <h5>결제예정금액</h5>
             <span>총 수량 : ${totalQuantity} 개</span>
        </div>

        <hr>
        <div class="d-flex justify-content-between mb-2">
            <span>총 상품금액</span><span style="text-decoration: line-through">${totalPrice.toLocaleString()}원</span>
        </div>
        <div class="d-flex justify-content-between mb-2">
            <span>할인 금액</span><span>-${(totalPrice - priceAfterDiscount).toLocaleString()}원</span>
        </div>
        <div class="summary-total d-flex justify-content-between">
            <span>총 결제 금액</span><span>${priceAfterDiscount.toLocaleString()}원</span>
        </div>
        <div class="summary-buttons mt-4 d-grid gap-2">
            <button class="btn btn-black">전체상품주문</button>
            <button class="btn btn-outline-dark">선택상품주문</button>
        </div>
    `;
}
//이벤트 위임
itemBody.addEventListener('click', function(e) {
    // 가장 가까운 cart-item 부모 찾기
    const cartItem = e.target.closest('.cart-item');
    let productId, cartId;
    if (cartItem) {
        productId = cartItem.getAttribute('data-product-id');
        cartId = cartItem.getAttribute('data-product-cart-id');

    }
    // 버튼(상세보기, 주문하기, +, - 등)에만 반응
    if ( e.target.classList.contains('detail-btn')) {
        location.href=`/product/detail/${productId}`;
    }
    if (e.target.classList.contains('order-btn')) {
        alert(`productId: ${productId}, cartId: ${cartId}`);
    }
    if(e.target.classList.contains('plus-btn')) {
        alert(`productId: ${productId}, cartId: ${cartId}`);
        plusBtnFunc(e.target);
    }
    if(e.target.classList.contains('minus-btn')){
        alert(`productId: ${productId}, cartId: ${cartId}`);
        minusBtnFunc(e.target);
    }
    if(e.target.classList.contains('update-qty-btn')) {
        const inputElement = e.target.previousElementSibling;
        const newQuantity = inputElement.value;
        axios.put(`/api/cart/${cartId}`, { quantity: newQuantity })
            .then(response => {
                console.log(response.data);
                alert('수량이 변경되었습니다.');
                getMyCartProducts();
            })
            .catch(error => {
                console.error('Error updating quantity:', error);
            });
    }
});
function checkUpdateBtnState(inputElement) {
    const original = inputElement.getAttribute('data-original-value');
    const updateBtn = inputElement.parentElement.querySelector('.update-qty-btn');
    updateBtn.disabled = inputElement.value === original;
}
plusBtnFunc = (plusBtnElement) => {
    //input 요소 찾기
    const inputElement = plusBtnElement.previousElementSibling;
    // 1씩 증가
    inputElement.value = parseInt(inputElement.value) + 1;
    checkUpdateBtnState(inputElement);
}
minusBtnFunc = (minusBtnElement) => {
    //input 요소 찾기
    const inputElement = minusBtnElement.nextElementSibling;
    // 1씩 감소
    if (parseInt(inputElement.value) > 1) {
        inputElement.value = parseInt(inputElement.value) - 1;
        checkUpdateBtnState(inputElement);
    }
}


getMyCartProducts();