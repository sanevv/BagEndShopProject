<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../include/header.jsp" />

<link rel="stylesheet" href="/aery/css/wish/wish.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
    const currentPage = ${currentShowPageNo};

    $(document).ready(function () {
        $("#selectAll").change(function () {
            $(".wishCheck").prop("checked", this.checked);
        });

        $("#deleteSelected").click(function () {
            const selected = $(".wishCheck:checked").map(function () {
                return $(this).data("id");
            }).get();

            if (selected.length === 0) {
                alert("삭제할 상품을 선택해주세요.");
                return;
            }

            if (confirm("선택한 상품을 삭제하시겠습니까?")) {
                $.ajax({
                    url: "deleteSelected.team1",
                    type: "post",
                    traditional: true,
                    data: { productIds: selected },
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            alert("삭제 완료");

                            const newPage = currentPage > 1 ? currentPage - 1 : 1;
                            location.href = "?currentShowPageNo=" + newPage;
                        } else {
                            alert(data.message || "삭제 실패");
                        }
                    },
                    error: function (xhr) {
                        alert("삭제 요청 실패: " + xhr.status);
                    }
                });
            }
        });
    });

    function removeWish(productId) {
        if (confirm("해당 상품을 삭제하시겠습니까?")) {
            $.ajax({
                url: "/deleteOne.team1",
                type: "post",
                data: { productId: productId },
                dataType: "json",
                success: function (response) {
                    if (response.success) {
                        alert(response.message);
                        location.reload();
                    } else {
                        alert(response.message || "삭제 실패");
                    }
                },
                error: function (xhr) {
                    alert("삭제 요청 실패: " + xhr.status);
                }
            });
        }
    }

    function wishToCart(productId) {
        $.ajax({
            url: "/wishToCart.team1",
            type: "post",
            data: { productId: productId },
            success: function () {
                alert("장바구니에 담겼습니다.");
            },
            error: function (xhr) {
                alert("장바구니 담기 실패: " + xhr.status);
            }
        });
    }

    function goToDetail(productId) {
        location.href = "/product/detail/" + productId;
    }

    function wishToOrder(productId) {
        if (!confirm("이 상품을 주문하시겠습니까?")) return;
        const orderData = [{ productId: productId, quantity: 1 }];
        submitwishToOrderForm(orderData);
    }

    function submitwishToOrderForm(orderProductRequests) {
        const form = document.createElement("form");
        form.method = "POST";
        form.action = "/order/confirm";

        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "orderProductRequestsJson";
        input.value = JSON.stringify(orderProductRequests);

        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();
    }
</script>

<div class="wishlist-container container">
    <!-- 좌측 마이페이지 메뉴 -->
    <div class="mypageMenu">
        <jsp:include page="../../include/mypageMenu.jsp" />
    </div>

    <!-- 관심상품 리스트 -->
    <div class="wish-content" style="margin-top: 50px;">
        <div class="wishlist-header">
            <h4 class="wish-title" style="padding-left: 20px;">관심 상품</h4>
        </div>

        <div class="wishlist-toolbar" style="margin: 15px 0; gap: 10px;">
            <label style="padding-left: 20px;"><input type="checkbox" id="selectAll" /></label>
            <button id="deleteSelected" class="button btn-outline-dark btn-sm">선택 삭제</button>
        </div>

        <div class="wish-list">
            <c:forEach var="wish" items="${wishProductList}">
                <div class="wish-item">
                    <div class="wish-info-row">
                        <div class="wish-left">
                            <input type="checkbox" class="wishCheck" data-id="${wish.productId}">
                            <div class="product-name">${wish.productName}</div>
                            <div class="wish-price">
                                <del><fmt:formatNumber value="${wish.productPrice}" pattern="#,###"/>원</del>&nbsp;
                                <span><fmt:formatNumber value="${wish.priceAfterDiscount}" pattern="#,###"/>원</span>
                            </div>
                        </div>
                        <div class="wish-image">
                            <img src="${wish.productThumbnailUrl}" alt="상품 이미지" onclick="goToDetail(${wish.productId})">
                            <button class="wish-delete-button" onclick="removeWish(${wish.productId})">×</button>
                        </div>
                    </div>
                    <div class="wish-actions">
                        <button class="button btn-outline-dark btn-sm" onclick="goToDetail(${wish.productId})">상세보기</button>
                        <div class="right-group">
                            <button class="button btn-outline-dark btn-sm" onclick="wishToCart(${wish.productId})">장바구니</button>
                            <button class="button btn-outline-dark btn-sm" onclick="wishToOrder(${wish.productId})">주문하기</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- 페이지네이션 -->
        <div class="pagination-container text-center mt-4" style="margin-top: 50px;">
            <nav aria-label="Page navigation">
                <ul class="pagination-custom">
                    <li class="${currentShowPageNo == 1 ? 'disabled' : ''}">
                        <a href="?currentShowPageNo=${currentShowPageNo - 1}">&lt;</a>
                    </li>
                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                        <li class="${i == currentShowPageNo ? 'active' : ''}">
                            <a href="?currentShowPageNo=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="${currentShowPageNo == totalPage ? 'disabled' : ''}">
                        <a href="?currentShowPageNo=${currentShowPageNo + 1}">&gt;</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<jsp:include page="../../include/footer.jsp" />
