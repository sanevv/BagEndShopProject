<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product/productDetail.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review/review.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../include/header.jsp"></jsp:include>

<script type="text/javascript">
function goDelete(noticeId) {
    if (confirm("이 상품 삭제해?")) {

        $.ajax({
            url: "${pageContext.request.contextPath}/prod/deleteProd",
            type: "post",
            data: { "product_id":"${prdVO.productId}"},
            dataType: "json",
            success: function(response) {
                alert("삭제 완료!");
                location.href = "${pageContext.request.contextPath}/product/list";
            },
            error: function(request, status, error) {
                alert("code: " + request.status + "\n" +
                      "message: " + request.responseText + "\n" +
                      "error: " + error);
            }
        });
    }
}
</script>



<main id="main">

	<div class="product-container">
		<div class="product-banner-top">
			<c:if test="${not empty prdVO.productImagePath}">
				<div class="product-banner-container">
					<c:if test="${not empty productAddImageList and productAddImageList.size() > 1}">
						<div class="thumbnails-img">
							<span class="thumb active"><img src="${prdVO.productImagePath}" alt="${prdVO.productName} 대표 이미지"/></span>
							<c:forEach var="item" items="${productAddImageList}">
								<span class="thumb"><img src="${item.productAddImagePath}" alt="${prdVO.productName} 추가 이미지"></span>
							</c:forEach>
						</div>
					</c:if>
					<c:if test="${not empty loginUser && loginUser.roleId == 1}">
						<div class="dropdown menu-dropdown">
							<button class="btn btn-light btn-sm dropdown-toggle border-0"type="button" id="dropdownMenuButton" data-bs-toggle="dropdown"aria-expanded="false" title="관리 메뉴">
								<i class="bi bi-gear-fill"></i>
							</button>
							<ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
								<li>		
									<form method="post" action="${pageContext.request.contextPath}/prod/update">
										<button class="btn btn-sm" type="submit">수정하기</button>
										<input type="hidden" name="productId" value="${prdVO.productId}">
									</form>
								</li>
								<li><a class="dropdown-item text-danger" href="#" data-id="${nvo.notice_id}" onclick="goDelete(this)">삭제하기</a></li>
							</ul>
							
						</div>
					</c:if>
					<div class="representative-img">
						<button class="slide-btn prev" onclick="backimg()">&#10094;</button>
						<img id="mainImage" src="${prdVO.productImagePath}" alt="${prdVO.productName} 이미지"/>
						<button class="slide-btn next" onclick="nextimg()">&#10095;</button>
					</div>
				</div>
			</c:if>
			<c:if test="${empty prdVO.productImagePath}">
				<img src="${pageContext.request.contextPath}/images/error/zz.png" alt="메렁~" style="margin: 0 auto;" />
			</c:if>
		</div>
		<div class="product-info-container">
			<div class="inner">
				<div class="product-detail-info">
					소재 : ${prdVO.matter} <br>
					사이즈 : ${prdVO.productSize} (mm) <br>
					<p>${prdVO.productInfo}</p>
				</div>

				<div class="product-sticky-info">
					<form id="addCartForm" name="addCartForm">
						<div class="product-info">
							<p class="product-name">${prdVO.productName}</p>
							<div class="product-price">
								<p class="before_price"><fmt:formatNumber value="${prdVO.price}" type="number" maxFractionDigits="0" />원</p>
								<p class="discount">
									<span class="discount-rate">${prdVO.discountRate}%</span>
									<span class="price"><fmt:formatNumber value="${prdVO.discountedPrice}" type="number" maxFractionDigits="0" /> <span>원</span></span>
								</p>
								<p class="quantity">
									<button type="button" class="btn btn-min">-</button>
									<input type="text" name="quantity" value="1" min="1" max="100" readonly />
									<button type="button" class="btn btn-plus">+</button>
								</p>
							</div>
							<div class="total-price">
								<span>총 상품금액</span>
								<span class="total-amount">0원</span>
							</div>
							<div class="product-buttons">
								<button type="button" id="btnAddCart" class="btn btn-cart">장바구니 담기</button>
								<button type="button" id="btnBuy" class="btn btn-buy" onclick="directOrder(isLogin)">바로 구매하기</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- 리뷰 -->
			<div class="review-container">
				<div class="review-header">
					<h2>REVIEW</h2>
					<button type="button" class="btn btn-review-write" onclick="isLoginCheck('write')">리뷰쓰기</button>
				</div>
				<div class="review-body">
					<ul id="reviewList" class="review-list"></ul>
				</div>
				<div class="review-footer" id="pageBar"></div>
			</div>
			<!-- //리뷰 -->


			<div class="product-contents">
				<!-- 상품 이미지 둥록 시 부활 예정 -->
<%--				<img src="${pageContext.request.contextPath}${prdVO.productContents}" alt="상세설명 이미지" /> --%>
				<img src="${pageContext.request.contextPath}/images/product/5/content5.png" alt="" >
				<img src="${pageContext.request.contextPath}/images/product/5/content5-1.png" alt="" >
				<img src="${pageContext.request.contextPath}/images/product/5/content5-2.png" alt="" >
			</div>
		</div>
	</div>


	<!-- 관리자 댓글 작성하기 -->
	<div class="modal fade" id="reviewCommentModal" tabindex="-1" aria-hidden="true">
		<form id="reviewCommentForm" name="reviewCommentForm">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">관리자 댓글 작성하기</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<textarea id="commentContents" name="reviewCommentContents" placeholder="작성해주세요."></textarea>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
						<button type="button" id="btnCommentSubmit" class="btn btn-primary">작성완료</button>
					</div>
				</div>
			</div>
		</form>
	</div>

	<!-- 관리자 댓글 수정하기 -->
	<div class="modal fade" id="reviewCommentUpdateModal" tabindex="-1" aria-hidden="true">
		<form id="reviewCommentUpdateForm" name="reviewCommentUpdateForm">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">관리자 댓글 수정하기</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<textarea id="commentUpdateContents" name="reviewCommentContents" placeholder="작성해주세요."></textarea>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
						<button type="button" id="btnCommentUpdate" class="btn btn-primary">수정완료</button>
					</div>
				</div>
			</div>
		</form>
	</div>

	<input type="hidden" name="reviewUserName" value="${prdVO.reviewUserName}">
	<input type="hidden" name="userId" value="${userId}">
	<input type="hidden" name="productId" value="${prdVO.productId}">

</main>

<script src="${pageContext.request.contextPath}/js/product/productDetail.js"></script>
<script src="${pageContext.request.contextPath}/js/review/review.js"></script>
<script src="${pageContext.request.contextPath}/js/cart/myCart.js"></script>
<script>

	const productTumbnails = document.querySelectorAll(".thumb");
	const btnSlide = document.querySelectorAll(".slide-btn");

	let productTumbnailsList = [];
	let currentIndex = 0;

	if(productTumbnails.length < 1) {
		btnSlide.forEach(btn => btn.style.display = "none");
	}
	else {
		productTumbnails.forEach((thumb, i) => {
			productTumbnailsList.push(thumb.img);
			thumb.addEventListener("click", () => {
				showImg(i);
			});
		})
	}

	function showImg(index) {
		const img = document.getElementById("mainImage");

		if(productTumbnailsList.length < 1) return;

		//img.src = productTumbnailsList[index];
		//console.log(productTumbnailsList[index]);
		currentIndex = index;

		// 썸네일에 active 클래스 관리
		productTumbnails.forEach((thumb, i) => {
			if (i === index) {
				thumb.classList.add("active");
				img.src = thumb.children[0].src;
			} else {
				thumb.classList.remove("active");
			}
		});
	}

	function backimg() {
		if(currentIndex-1 >= 0) {
			currentIndex -= 1;
			showImg(currentIndex);
		}
		else{
			currentIndex = productTumbnailsList.length-1;
			showImg(currentIndex);
		}
	}

	function nextimg() {
		if(currentIndex+1 < productTumbnailsList.length) {
			currentIndex += 1;
			showImg(currentIndex);
		}
		else{
			currentIndex = 0;
			showImg(currentIndex);
		}
	}

</script>
<jsp:include page="../include/footer.jsp"></jsp:include>