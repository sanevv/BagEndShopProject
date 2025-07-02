<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../include/header.jsp" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user/wish.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
    // 전체 선택
    $("#selectAll").change(function() {
        $(".wishCheck").prop("checked", this.checked);
    });

    // 선택 삭제
    $("#deleteSelected").click(function() {
        const selected = $(".wishCheck:checked").map(function() {
            return $(this).data("id");
        }).get();

        if (selected.length === 0) {
            alert("삭제할 상품을 선택해주세요.");
            return;
        }

        if (confirm("선택한 상품을 삭제하시겠습니까?")) {
            axios.delete("<%=request.getContextPath()%>/api/wish", {
                data: selected
            }).then(res => {
                alert(res.data.message || "삭제 완료");
                location.reload();
            }).catch(err => {
                alert("삭제 요청 실패");
                console.error(err);
            });
        }
    });
});

function removeWish(productId) {
    if (confirm("해당 상품을 삭제하시겠습니까?")) {
        axios.delete("<%=request.getContextPath()%>/api/wish", {
            data: [productId]
        }).then(res => {
            alert(res.data.message || "삭제 성공");
            location.reload();
        }).catch(err => {
            alert("삭제 실패");
            console.error(err);
        });
    }
}

function wishToCart(productId) {
    axios.post("<%=request.getContextPath()%>/api/cart", {
        productId: productId,
        quantity: 1
    }).then(res => {
        alert(res.data.message || "장바구니에 담겼습니다.");
    }).catch(err => {
        alert("장바구니 담기 실패");
        console.error(err);
    });
}

function goToDetail(productId) {
    location.href = "<%=request.getContextPath()%>/product/detail/" + productId;
}

function wishOrder(productId) {
    if (!confirm("이 상품을 주문하시겠습니까?")) return;

    const orderData = [{ productId: productId, quantity: 1 }];
    submitWishOrderForm(orderData);
}

function submitWishOrderForm(orderProductRequests) {
    const form = document.createElement("form");
    form.method = "POST";
    form.action = "<%=request.getContextPath()%>/order/confirm";

    const input = document.createElement("input");
    input.type = "hidden";
    input.name = "orderProductRequestsJson";
    input.value = JSON.stringify(orderProductRequests);

    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}
</script>

<div class="wishlist-container">
    <div class="mypageMenu">
        <jsp:include page="../../include/mypageMenu.jsp"/>
    </div>

    <div class="wish-content" style="margin-top:75px;">
        <div class="wishlist-header">
            <h4 class="wish-title">관심 상품</h4>
        </div>

        <div class="wishlist-toolbar" style="display: flex; align-items: center; justify-content: flex-start; margin: 15px 0; gap: 10px;">
            <label style="padding-left: 20px;">
                <input type="checkbox" id="selectAll"> 전체 선택
            </label>
            <button id="deleteSelected" class="button btn-sm">선택 삭제</button>
        </div>

        <div class="wish-list">
            <c:forEach var="wish" items="${wishProductList}">
                <div class="wish-item">
                    <div class="wish-info-row">
                        <div class="wish-left">
                            <input type="checkbox" class="wishCheck" data-id="${wish.productId}" style="margin-bottom: 15px;">
                            <div class="product-name" style="margin-bottom: 15px;">${wish.productName}</div>
                            <div class="wish-price">
                                <del><fmt:formatNumber value="${wish.productPrice}" pattern="#\,###"/>원</del>&nbsp;&nbsp;
                                <fmt:formatNumber value="${wish.priceAfterDiscount}" pattern="#\,###"/>원
                            </div>
                        </div>

                        <div class="wish-image">
                            <img src="${wish.productThumbnailUrl}" alt="상품 이미지">
                            <button class="wish-delete-button" onclick="removeWish(${wish.productId})">×</button>
                        </div>
                    </div>

                    <div class="wish-actions">
                        <button class="button btn-outline-dark btn-sm" onclick="goToDetail(${wish.productId})">상세보기</button>
                        <div class="right-group">
                            <button class="button btn-outline-dark btn-sm" onclick="wishToCart(${wish.productId})">장바구니</button>
                            <button class="button btn-outline-dark btn-sm" onclick="wishOrder(${wish.productId})">주문하기</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="pagination-container text-center mt-4">
            <ul id="pagination" class="pagination justify-content-center"></ul>
        </div>
    </div>
</div>

<jsp:include page="../../include/footer.jsp"/>