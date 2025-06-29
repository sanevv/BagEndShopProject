
const itemBody = document.getElementById('itemBody');
const paymentBody = document.getElementById('paymentBody');
document.getElementById('allCheck').checked = true;

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



let totalPrice = 0;
let totalQuantity = 0;
let priceAfterDiscount = 0;
let responseObjects = {};//불러올때 대괄호안에 키값넣어주면됨

responseToVariable = (cartProduct) => {
    responseObjects = cartProduct.reduce((acc, item) => {
        const { productId, ...rest } = item; // productId를 제외한 나머지 속성들
        acc[productId] = rest; // productId를 키로 사용하여 나머지 속성을 저장
        return acc;
    }, {})
}

showMyCartProducts = (cartProducts) => {
    responseToVariable(cartProducts);
    itemBody.innerHTML = cartProducts.map(product => {
        totalPrice += product.price * product.quantity;
        totalQuantity += product.quantity;
        // 할인된 가격 계산
        const discountPrice = Math.round(product.price * (1 - (product.discountRate || 1)));
        priceAfterDiscount += ( discountPrice * product.quantity );
        return `
        <div class="cart-item position-relative d-flex align-items-center" data-product-id="${product.productId}" data-product-cart-id="${product.productCartId}">
            <input type="checkbox" class="me-3 order-check" checked>
            <div class="me-4 item-image">
                <img class="detail-btn" src="${product.productImage}" alt="제품">
            </div>
            <div class="flex-grow-1">
                <h5 class="detail-btn" style="font-weight: bold; font-size: 18px; margin-bottom: 10px">${product.productName}</h5>
                <div style="display: flex;">
                    <p class="text-muted mb-1 me-2" style="text-decoration: line-through" data-product-price="${product.price}">${product.price ? product.price.toLocaleString() + '원' : ''}</p>
                    <p class="mb-1" style="color: #ff4d52">${product.discountRate ? product.discountRate * 100 + '% 할인' : '할인 없음'}</p>
                </div>
                
                <div class="d-flex align-items-center justify-content-between mb-2 item-qty">
                    <span class="text-muted" style="margin-bottom: -20px; font-size: 17px">${discountPrice.toLocaleString()+'원'}</span>
                    <div class="d-flex align-items-center">
                        <button class="btn btn-outline-secondary btn-sm me-1 minus-btn">-</button>
                        <input type="text" class="form-control form-control-sm me-1" data-original-value="${product.quantity}" value="${product.quantity}" readonly>
                        <button class="btn btn-outline-secondary btn-sm plus-btn me-2">+</button>
                        <button class="btn btn-primary btn-sm update-qty-btn" style="font-size: 13px" disabled>수량변경</button>
                    </div>
                </div>
                <div class="d-flex align-items-center justify-content-end mb-2">
                    <button class="btn btn-outline-dark btn-sm me-2 detail-btn" style="font-size: 14px; font-weight: normal; margin-bottom: 0">상세보기</button>
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
             <span id="totalQuantity" data-total-quantity="${totalQuantity}">총 수량 : ${totalQuantity} 개</span>
        </div>

        <hr>
        <div class="d-flex justify-content-between mb-2">
            <span>총 상품금액</span>
            <span style="text-decoration: line-through" id="totalPrice" data-at-total-price="${totalPrice}">${totalPrice.toLocaleString()}원</span>
        </div>
        <div class="d-flex justify-content-between mb-2">
            <span>할인 금액</span>
            <span id="discountPrice" data-at-discount-price="${totalPrice - priceAfterDiscount}">-${(totalPrice - priceAfterDiscount).toLocaleString()}원</span>
        </div>
        <div class="summary-total d-flex justify-content-between">
            <span>총 결제 금액</span><span id="priceAfterDiscount" data-at-price-after-discount="${priceAfterDiscount}">${priceAfterDiscount.toLocaleString()}원</span>
        </div>
        <div class="summary-buttons mt-4 d-grid gap-2">
            <button class="btn btn-black" id="allOrderBtn">전체상품주문</button>
            <button class="btn btn-outline-dark" id="selectedOrderBtn">선택상품주문</button>
        </div>
    `;
}

//수량변경함수
requestModifyQuantity = (productCartId, quantity, productName, btnElement, quantityElement) =>{
    axios.put('/api/cart', {}, {
        params: {
            productCartId: productCartId,
            quantity: quantity
        }
    })
        .then(response => {
            console.log(response.data);
            alert(`"${productName}" 상품의 수량이 ${quantity}개로 변경되었습니다.`);
            // 수량 변경 후 버튼 비활성화
            btnElement.disabled = true;
            // input 요소의 data-original-value 속성 업데이트
            quantityElement.setAttribute('data-original-value', quantity);
        })
        .catch(error => {
            console.error('수량 변경 실패:', error);
            alert(`"${productName}" 상품의 수량 변경에 실패했습니다.`);
        })
}
//수량변경함수 페이지 이동전
requestModifyQuantityEndFunc = (productCartId, quantity) =>{
    axios.put('/api/cart', {}, {
        params: {
            productCartId: productCartId,
            quantity: quantity
        }
    })
        .then(response => {
            console.log(response.data);
        })
        .catch(error => {
            console.error('수량 변경 실패:', error);
        })
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
    // ------------------단일 상품 주문 이벤트 처리------------------------------
    if(e.target.classList.contains('order-btn')) {
        const cartItem = e.target.closest('.cart-item');
        const productId = cartItem.getAttribute('data-product-id');
        const cartId = cartItem.getAttribute('data-product-cart-id');
        const quantity = cartItem.querySelector('input[type="text"]').value;


        if(!confirm(`상품 "${cartItem.querySelector('h5').innerText}"을 주문하시겠습니까?`)) return;

        const updateBtn = cartItem.querySelectorAll('.update-qty-btn')
        if(checkQuantityUpdateButtons(updateBtn)){
            alert('현재 상품의 변경된 수량이 적용된 후 결제 페이지로 이동합니다.')
            requestModifyQuantityEndFunc(cartId, quantity);
        }



        requestOrderProducts([{ productId: productId, quantity: quantity, cartId: cartId }]);
    }// ------------------단일 상품 주문 이벤트 끝------------------------------

    if(e.target.classList.contains('plus-btn')) {
        plusBtnFunc(e.target, responseObjects[productId].price, responseObjects[productId].discountRate);
        responseObjects[productId].quantity += 1; // 수량 증가
    }
    if(e.target.classList.contains('minus-btn')){
        minusBtnFunc(e.target, responseObjects[productId].price, responseObjects[productId].discountRate);
        responseObjects[productId].quantity -= 1; // 수량 감소
    }

    if(e.target.classList.contains('update-qty-btn')) {
        const inputElement = e.target.parentElement.querySelector('input');
        const newQuantity = inputElement.value;
        requestModifyQuantity(cartId, newQuantity, cartItem.querySelector('h5').innerText, e.target, inputElement);
    }
    if(e.target.classList.contains('item-remove')){
        //cartItem 요소 안의 h5 태그 찾기
        const productNameElement = cartItem.querySelector('h5');

        if(confirm(`장바구니에서 "${productNameElement.innerText}" 상품을 삭제하시겠습니까?`))
            requestDeleteCartProduct(productId);
    }

});
paymentBody.addEventListener('click', function(e) {
    // 전체상품 주문 버튼 클릭 이벤트 처리------------------------------
    if(e.target.id === 'allOrderBtn') {
        const allCartItemList = itemBody.querySelectorAll('.cart-item');
        if(allCartItemList.length === 0) {
            alert('장바구니에 담긴 상품이 없습니다.');
            return;
        }
        if(!confirm('장바구니의 모든 상품을 주문하시겠습니까?')) return;
        //productId와 quantity 를 모아 주문 요청 productId 키값 = value ; 형식의 객체생성
        const updateBtnList = Array.from(allCartItemList).map(item => item.querySelector('.update-qty-btn'));
        if(checkQuantityUpdateButtons(updateBtnList)) {
            const enabledElements = Array.from(allCartItemList).filter(item=> item.querySelector('.update-qty-btn:not(:disabled)'));
            alert('상품의 변경된 수량이 적용된 후 결제 페이지로 이동합니다.');
            enabledElements.forEach(item => {//수량변경이 안된상품들 수량변경적용
                const cartId = item.getAttribute('data-product-cart-id');
                const quantity = item.querySelector('input[type="text"]').value;
                requestModifyQuantityEndFunc(cartId, quantity);
            });
        }


        const orderProducts = Array.from(allCartItemList).map(item => {
            const productId = item.getAttribute('data-product-id');
            const cartId = item.getAttribute('data-product-cart-id');
            const quantity = item.querySelector('input[type="text"]').value;
            return { productId: productId, quantity: quantity, cartId: cartId };
        });

        requestOrderProducts(orderProducts);
    }// --------------전체상품 주문 끝 -------------------------------------------------

    // 선택상품 주문 버튼 클릭 이벤트 처리------------------------------
    if(e.target.id === 'selectedOrderBtn') {
        const selectedCheckboxes = itemBody.querySelectorAll('.order-check:checked');
        if (selectedCheckboxes.length === 0) {
            alert('주문할 상품을 선택해주세요.');
            return;
        }
        if(!confirm('선택한 상품을 주문하시겠습니까?')) return;
        // 선택된 체크박스의 부모 요소 리스트를 생성
        const selectedCartItems = Array.from(selectedCheckboxes).map(checkbox =>
            checkbox.closest('.cart-item')
        )
        const updateBtnList = selectedCartItems.map(item => item.querySelector('.update-qty-btn'));

        if(checkQuantityUpdateButtons(updateBtnList)) {
            const enabledElements = selectedCartItems.filter(item=> item.querySelector('.update-qty-btn:not(:disabled)'));
            alert('현재 선택한 상품의 변경된 수량이 적용된 후 결제 페이지로 이동합니다.');
            enabledElements.forEach(item => {//수량변경이 안된상품들 수량변경적용
                const cartId = item.getAttribute('data-product-cart-id');
                const quantity = item.querySelector('input[type="text"]').value;
                requestModifyQuantityEndFunc(cartId, quantity);
            });
        }




        //productId와 quantity 를 모아 주문 요청 productId 키값 = value ; 형식의 객체생성
        const orderProducts = Array.from(selectedCheckboxes).map(checkbox => {
            const cartItem = checkbox.closest('.cart-item');
            const productId = cartItem.getAttribute('data-product-id');
            const cartId = cartItem.getAttribute('data-product-cart-id');
            const quantity = cartItem.querySelector('input[type="text"]').value;
            return { productId: productId, quantity: quantity, cartId: cartId };
        });
        requestOrderProducts(orderProducts);
    }// --------------선택상품 주문 끝 -------------------------------------------------


})

//페이지 이동을위해서는 axios와 ajax 이용 불가 form 을 활용해야한다.
function requestOrderProducts(orderProductRequests) {
    const form = document.createElement('form');
    form.method = 'POST';// POST 방식으로 전송
    form.action = '/order/confirm';// 주문 확인 페이지로 이동

    const input = document.createElement('input');
    input.type = 'hidden';// 숨겨진 input 요소 생성
    input.name = 'orderProductRequestsJson';// input 요소의 name 속성 설정
    input.value = JSON.stringify(orderProductRequests);// orderProductRequests를 JSON 문자열로 변환
    form.appendChild(input);//만든 input을 form에 추가

    document.body.appendChild(form);//만든 form을 body에 추가
    form.submit();
}


//checkbox 관련 이벤트 처리 -------------------------------------------------------------------
const allCheckBox = document.getElementById('allCheck');
itemBody.addEventListener('change', (e) => {
    if(e.target.classList.contains('order-check')){
        const isChecked = e.target.checked;
        if (!isChecked)
            allCheckBox.checked = false;
        else {
            const allCheckboxes = itemBody.querySelectorAll('.order-check');
            // 모든 체크박스가 체크되어 있는지 확인
            allCheckBox.checked = Array.from(allCheckboxes).every(checkbox => checkbox.checked);
        }
    }
})
allCheckBox.addEventListener('change', (e) => {
    const isChecked = e.target.checked;
    const checkboxes = itemBody.querySelectorAll('.order-check');
    checkboxes.forEach(checkbox => {
        checkbox.checked = isChecked;
    });
})
//checkbox 선택 삭제 버튼 이벤트 처리
const selectedDelete = document.getElementById('selectedDelete');
selectedDelete.addEventListener('click', () => {
    const selectedCheckboxes = itemBody.querySelectorAll('.order-check:checked');
    if (selectedCheckboxes.length === 0) {
        alert('삭제할 상품을 선택해주세요.');
        return;
    }
    const productIds = Array.from(selectedCheckboxes).map(checkbox => {
        const cartItem = checkbox.closest('.cart-item');//가장 가까운 위쪽 조상
        return cartItem.getAttribute('data-product-id');
    })
    if(confirm(`선택한 ${productIds.length}개의 상품을 장바구니에서 삭제하시겠습니까?`)) {
        requestDeleteCartProduct(productIds);
    }
})
//checkbox 이벤트 처리 끝 -------------------------------------------------------------------

//수량변경버튼의 활성화 유무 확인
function checkUpdateBtnState(inputElement) {
    const original = inputElement.getAttribute('data-original-value');
    const updateBtn = inputElement.parentElement.querySelector('.update-qty-btn');
    updateBtn.disabled = inputElement.value === original;
}


plusBtnFunc = (plusBtnElement, price, discountRate) => {
    console.log(price);
    console.log(discountRate);
    const totalQuantityElement = document.getElementById('totalQuantity');
    const totalPriceElement = document.getElementById('totalPrice');
    const priceAfterDiscountElement = document.getElementById('priceAfterDiscount');
    const discountPriceElement = document.getElementById('discountPrice');
    //input 요소 찾기
    const inputElement = plusBtnElement.previousElementSibling;
    // 1씩 증가
    inputElement.value = parseInt(inputElement.value) + 1;
    // 결제 예정금액 폼에 값을 업데이트
    const productPriceAfterDiscount = Math.round(price * (1 - (discountRate || 0)));// 현재 상품 할인된 가격 계산

    const atTotalQuantity = parseInt(totalQuantityElement.getAttribute('data-total-quantity'));
    const atTotalPrice = parseInt(totalPriceElement.getAttribute('data-at-total-price'));
    const atDiscountPrice = parseInt(discountPriceElement.getAttribute("data-at-discount-price"));
    const atPriceAfterDiscount = parseInt(priceAfterDiscountElement.getAttribute("data-at-price-after-discount"));
    //변화될 값
    const changeTotalQuantity = atTotalQuantity + 1;
    const changeTotalPrice = atTotalPrice + price;
    const changePriceAfterDiscount = atPriceAfterDiscount + productPriceAfterDiscount;
    const changeDiscountPrice = atDiscountPrice + productPriceAfterDiscount;






    totalQuantityElement.innerHTML = `총 수량 : ${changeTotalQuantity} 개`
    totalPriceElement.innerHTML = `${changeTotalPrice.toLocaleString()}원`;
    priceAfterDiscountElement.innerHTML = `${changePriceAfterDiscount.toLocaleString()}원`;
    discountPriceElement.innerHTML = `-${changeDiscountPrice.toLocaleString()}원`;
    //attribute 값 변경
    changeAttributeValue(totalPriceElement, 'data-at-total-price', changeTotalPrice);
    changeAttributeValue(priceAfterDiscountElement, 'data-at-price-after-discount', changePriceAfterDiscount);
    changeAttributeValue(discountPriceElement, 'data-at-discount-price', changeDiscountPrice);
    changeAttributeValue(totalQuantityElement, 'data-total-quantity', changeTotalQuantity);

    // 변경된 값이 원래 값과 다르면 버튼 활성화
    checkUpdateBtnState(inputElement);
}
changeAttributeValue = (element, attributeName, newValue) => {
    // 해당 요소의 특정 속성(attributeName)의 값을 newValue로 변경
    element.setAttribute(attributeName, newValue);
}


minusBtnFunc = (minusBtnElement, price,discountRate) => {
    //input 요소 찾기
    const inputElement = minusBtnElement.nextElementSibling;
    // 1씩 감소
    if (parseInt(inputElement.value) > 1) {
        const totalQuantityElement = document.getElementById('totalQuantity');
        const totalPriceElement = document.getElementById('totalPrice');
        const priceAfterDiscountElement = document.getElementById('priceAfterDiscount');
        const discountPriceElement = document.getElementById('discountPrice');

        inputElement.value = parseInt(inputElement.value) - 1;
        // 결제 예정금액 폼에 값을 업데이트
        const productPriceAfterDiscount = Math.round(price * (1 - (discountRate || 0)));// 현재 상품 할인된 가격 계산

        const atTotalQuantity = parseInt(totalQuantityElement.getAttribute('data-total-quantity'));
        const atTotalPrice = parseInt(totalPriceElement.getAttribute('data-at-total-price'));
        const atDiscountPrice = parseInt(discountPriceElement.getAttribute("data-at-discount-price"));
        const atPriceAfterDiscount = parseInt(priceAfterDiscountElement.getAttribute("data-at-price-after-discount"));
        //변화될 값
        const changeTotalQuantity = atTotalQuantity - 1;
        const changeTotalPrice = atTotalPrice - price;
        const changePriceAfterDiscount = atPriceAfterDiscount - productPriceAfterDiscount;
        const changeDiscountPrice = atDiscountPrice - productPriceAfterDiscount;

        totalQuantityElement.innerHTML = `총 수량 : ${changeTotalQuantity} 개`
        totalPriceElement.innerHTML = `${changeTotalPrice.toLocaleString()}원`;
        priceAfterDiscountElement.innerHTML = `${changePriceAfterDiscount.toLocaleString()}원`;
        discountPriceElement.innerHTML = `-${changeDiscountPrice.toLocaleString()}원`;
        //attribute 값 변경
        changeAttributeValue(totalPriceElement, 'data-at-total-price', changeTotalPrice);
        changeAttributeValue(priceAfterDiscountElement, 'data-at-price-after-discount', changePriceAfterDiscount);
        changeAttributeValue(discountPriceElement, 'data-at-discount-price', changeDiscountPrice);
        changeAttributeValue(totalQuantityElement, 'data-total-quantity', changeTotalQuantity);

        checkUpdateBtnState(inputElement);

    }
}



requestDeleteCartProduct = (productIds) =>{
    axios.delete('/api/cart', {
        params : {
            productIds: productIds
        },
        paramsSerializer: params => {
            return jQuery.param(params)
        }
    })
        .then(res=>{
            console.log(res.data);
            if(res.data.success) {
                alert('장바구니 상품이 삭제되었습니다.');
                getMyCartProducts();
            }
        })
        .catch(err=>{
            console.error('장바구니 상품 삭제 실패:', err);
            alert('장바구니 상품 삭제에 실패했습니다.');
        })
}

//수량 변경 버튼이 활성화 되어있는지 확인
checkQuantityUpdateButtons = (elementList) => {
    return Array.from(elementList).some(item =>
        item.disabled === false
    )}


getMyCartProducts();