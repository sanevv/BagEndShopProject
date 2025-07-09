requestOrderProductsTest = () => {
    axios.post('/api/order/confirm', orderProductRequests)
        .then(response => {
            console.log(response.data);
            if (response.data.success)
                viewProductInfo(response.data.success.responseData);
            else {
                alert('주문 상품을 불러오지 못했습니다.');
                history.back();
            }
        })
        .catch(error => {
            console.error('주문 페이지 이동 실패:', error);
            alert('요청에 실패했습니다.');
        })

}
let paymentRequests = [{}];
let refProductName = '';
viewProductInfo = (responseData) => {
    paymentRequests = responseData.map(item => ({
        productId: item.productId,
        productCartId: item.productCartId,
        atPrice: item.price,
        atDiscountRate: item.discountRate,
        quantity: item.quantity
    }));
    // 상품명만 추출 후 두 개만 선택
    refProductName = responseData
        .map(item => item.productName)
        .slice(0, 2)//앞에서부터 두개만 0, 1
        .join(', ') + '...'; // 나머지는 '...'로 표시


    let totalPrice = 0;
    let finalPrice = 0;
    let html = '';

    responseData.forEach(item => {
        const discounted = Math.round(item.price * (1 - item.discountRate));
        const originalTotal = item.price * item.quantity;
        const discountedTotal = discounted * item.quantity;


        totalPrice += originalTotal;
        finalPrice += discountedTotal;

        html += `
        <div class="order-item" data-product-id="${item.productId} data-product-cart-id="${item.productCartId} data-quantity="${item.quantity} data-price="${item.price} data-discount-rate="${item.discountRate}">
          <img src="${item.productImage}" alt="${item.productName}">
          <div class="item-info">
            <div class="item-name">${item.productName}</div>
            <div class="item-price">
              <span class="original">${item.price.toLocaleString()}원</span>
              <span class="discounted">${discounted.toLocaleString()}원</span>
            </div>
            <div style="display: flex;">
            <div class="item-quantity">수량: ${item.quantity}개</div>
            <div class="discounted-quantity"><span style="color: #323232">총 금액:  </span>${(discounted * item.quantity).toLocaleString()}원</div>
            </div>

          </div>
        </div>
      `;
    });

    document.getElementById('orderItems').innerHTML = html;
    document.getElementById('totalPrice').innerText = `${totalPrice.toLocaleString()}원`;
    document.getElementById('discountPrice').innerText = `-${(totalPrice - finalPrice).toLocaleString()}원`;
    document.getElementById('finalPrice').innerText = `${finalPrice.toLocaleString()}원`;
}


function requestPayment() {
    axios.post('/api/order/payment', paymentRequests)
        .then(response => {
            console.log(response.data);
            realPaymentPortOne(response.data.success.responseData)
        })
        .catch(error => {
            console.error('결제 요청 실패:', error);
            if(error.response && error.response.status === 400)
                alert("로그인 후 이용해주세요.");
            alert('결제 요청에 실패했습니다.');
            history.back();
        })
}

realPaymentPortOne = (data) => {
    alert('결제 요청이 성공적으로 처리되었습니다. 결제창을 띄웁니다.');
    const requestObject = {
        ordersId: data.ordersId,
        totalPrice: data.totalPrice,
        totalStock: data.totalStock,
        productCartIds: data.productCartIds,
        refProductName: refProductName
    };
    const requestJson = encodeURIComponent(JSON.stringify(requestObject));

    //팝업창 띄우기
    const url = ctxPath + '/order/payment?paymentRequestJson=' + requestJson;
    const width = 1000;
    const height = 600;
    const left = Math.ceil((window.screen.width - width) / 2); //화면의 너비
    const top = Math.ceil((window.screen.height - height) / 2); //화면의 높이
    window.open(url, 'ProductPayment', `left=${left}, top=${top}, width=${width}, height=${height}`);

}


requestOrderProductsTest();

//결제 성공시 함수
async function paymentSuccess(ordersId, productCartIds) {
    axios.post('/api/order/success', {}, {
            params: {
                ordersId: ordersId,
                productCartIds: productCartIds
            },
            paramsSerializer: params => {
                return jQuery.param(params)
            }
    })
        .then((res) => {
            console.log(res.data);
            if (res.data.success) {
                alert('결제가 완료되었습니다.');
                location.href = "/";
            }
        }).catch(error => {
        console.error('결제 성공 처리 중 오류 발생:', error);
        alert('결제 처리 중 오류가 발생했습니다. 다시 시도해주세요.');
        history.back();
    })

}

async function paymentFail(ordersId) {
    axios.delete('/api/order/fail', {params: {ordersId: ordersId}})
        .then((res) => {
            console.log(res.data);

            alert('결제에 실패하였습니다. 다시 시도해주세요.');
            history.back();
        })
        .catch(error => {
            console.error('결제 실패 처리 중 오류 발생:', error);
        })
}