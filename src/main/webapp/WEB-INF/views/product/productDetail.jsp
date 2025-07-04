<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product/productDetail.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review/review.css">

<jsp:include page="../include/header.jsp"></jsp:include>
<script>
	// 로그인 유무
</script>
<main id="main">
	<div class="product-container">
		<div class="product-banner-top">
			<c:if test="${not empty prdVO.productImagePath}">
				<div class="product-banner-container">
					<div class="thumbnails-img">
						<span class="thumb active"><img src="${prdVO.productImagePath}" alt="${prdVO.productName} 이미지"/></span>
						<span class="thumb"><img src="${pageContext.request.contextPath}/images/product/5/5-1.png" alt=""></span>
						<span class="thumb"><img src="${pageContext.request.contextPath}/images/product/5/5-2.png" alt=""></span>
						<span class="thumb"><img src="${pageContext.request.contextPath}/images/product/5/5-3.png" alt=""></span>
					</div>
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
								<button type="button" id="btnBuy" class="btn btn-buy">바로 구매하기</button>
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

	<input type="hidden" name="reviewUserName" value="${prdVO.reviewUserName}">
	<input type="hidden" name="userId" value="${userId}">
	<input type="hidden" name="productId" value="${prdVO.productId}">
</main>

<script src="${pageContext.request.contextPath}/js/product/productDetail.js"></script>
<script src="${pageContext.request.contextPath}/js/review/review.js"></script>
<script src="${pageContext.request.contextPath}/js/cart/myCart.js"></script>
<script>

	const imgList = [
		"${prdVO.productImagePath}",
		"${pageContext.request.contextPath}/images/product/5/5-1.png",
		"${pageContext.request.contextPath}/images/product/5/5-2.png",
		"${pageContext.request.contextPath}/images/product/5/5-3.png"
	];

	let currentIndex = 0;

	function showImg(index) {
		const img = document.getElementById("mainImage");
		img.src = imgList[index];
		currentIndex = index;

		// 썸네일에 active 클래스 관리
		const thumbnails = document.querySelectorAll(".thumb");
		thumbnails.forEach((thumb, i) => {
			if (i === index) {
				thumb.classList.add("active");
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
			currentIndex = imgList.length-1;
			showImg(currentIndex);
		}
	}

	function nextimg() {
		if(currentIndex+1 < imgList.length) {
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