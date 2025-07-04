document.addEventListener('DOMContentLoaded', () => {
    // 상세보기
    document.querySelectorAll('.detail-btn').forEach(button => {
        button.addEventListener('click', () => {
            const productId = button.getAttribute('data-id');
            location.href = `/productDetail.team1?productId=${productId}`;
        });
    });

    // 장바구니 담기
    document.querySelectorAll('.btn-cart').forEach(button => {
        button.addEventListener('click', () => {
            const productId = button.getAttribute('data-id');
            fetch('/wishToCart.team1', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: new URLSearchParams({ productId })
            })
            .then(res => res.text())
            .then(() => {
                alert('장바구니에 담겼습니다.');
            })
            .catch(err => {
                console.error('장바구니 담기 실패:', err);
                alert('장바구니 담기에 실패했습니다.');
            });
        });
    });

    // 주문하기
    document.querySelectorAll('.btn-order').forEach(button => {
        button.addEventListener('click', () => {
            const productId = button.getAttribute('data-id');
            if (!confirm('해당 상품을 주문하시겠습니까?')) return;
            location.href = `/wishToOrder.team1?productId=${productId}`;
        });
    });

    // 삭제
    document.querySelectorAll('.btn-remove').forEach(button => {
        button.addEventListener('click', () => {
            const productId = button.getAttribute('data-id');
            if (!confirm('관심상품에서 삭제하시겠습니까?')) return;
            fetch('/deleteWish.team1', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: new URLSearchParams({ productId })
            })
            .then(res => res.text())
            .then(() => {
                alert('삭제되었습니다.');
                location.reload();
            })
            .catch(err => {
                console.error('삭제 실패:', err);
                alert('삭제에 실패했습니다.');
            });
        });
    });
});