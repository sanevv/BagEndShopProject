<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String ctxPath = request.getContextPath();
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/product/product.css">
<script>
const imgList = [
	  "${pageContext.request.contextPath}/images/product/5/5.png",
	  "${pageContext.request.contextPath}/images/product/5/5-1.png",
	  "${pageContext.request.contextPath}/images/product/5/5-2.png",
	  "${pageContext.request.contextPath}/images/product/5/5-3.png"
	];

	let currentIndex = 0;

	function showImg(index) {
	  const img = document.getElementById("mainImage");
	  img.src = imgList[index];
	  currentIndex = index;
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

<jsp:include page="../include/header.jsp"></jsp:include>

<div class="product_container" id="detail">

	<div class="mainimg row">
		<div class="mainimg-slider">
			<button class="slide-btn prev" onclick="backimg()">&#10094;</button>

			<img id="mainImage"
				src="${pageContext.request.contextPath}/images/product/5/5.png"
				alt="상품 이미지" />

			<button class="slide-btn next" onclick="nextimg()">&#10095;</button>
		</div>

	</div>
	<div class="detailimg mt-5">
		<span><img alt=""
			src="${pageContext.request.contextPath}/images/product/5/5-1.png"></span>
		<span><img alt=""
			src="${pageContext.request.contextPath}/images/product/5/5-2.png"></span>
		<span><img alt=""
			src="${pageContext.request.contextPath}/images/product/5/5-3.png"></span>
	</div>
	<br>
	<hr style="border: solid 2px black;">
	<br>
	<div class="content row">

		<div class="col-md-8 mt-5" style="line-height: 25px; font-size: 14pt;">
			소재 PVC 현수막 사이즈 330 X 260 X 80 (mm) 무게 740 (g)<br> 1. 가방은 폐기된 광고
			현수막을 업사이클링하였고, 가방 스트랩은 안전벨트를 업사이클링하여 제작되었습니다. 모든 소재는 100% 방수 가능합니다.<br>
			2. 노트북 13인치까지 수납 가능한 사이즈로 데일리 백으로 특히 잘 어울립니다.<br> 3. 전면 지퍼 포켓,
			내부 지퍼 포켓&벨크로 포켓이 있습니다. 가방 안쪽에는 태블릿 또는 노트를 담기 위한 별도 수납 공간이 있습니다. <br>4.
			자전거를 타신다면? 라이더를 위해 가방이 흘러내리지 않도록 잡아주는 추가 스트랩과 가방 양쪽 3M 반사판이 있어 안전한
			라이딩을 할 수 있습니다.
		</div>

		<div class="col-md-4 product-box" style="line-height: 30px">
			<div class="product-name mb-2">가방</div>

			<div class="product-info">
				<div class="label-original-price">원가</div>
				<div class="discount-section">
					<span class="discount-rate">70%</span> <span class="discount-price">45,600원</span>
				</div>
			</div>

			<hr class="divider" />

			<div class="total-price">
				<span style="font-size: 14pt">총 상품금액</span> <span
					class="total-amount">0 (0개)</span>
			</div>

			<div class="action-buttons">
				<button class="btn btn-cart">장바구니 담기</button>
				<button class="btn btn-buy">바로 구매하기</button>
			</div>
		</div>




		<div style="margin: auto;">
			<div class="contentimg"
				style="margin-top: 30px; border: solid 0px red;">
				<img alt=""
					src="${pageContext.request.contextPath}/images/product/5/content5.png">
				<img alt=""
					src="${pageContext.request.contextPath}/images/product/5/content5-1.png">
				<img alt=""
					src="${pageContext.request.contextPath}/images/product/5/content5-2.png">
			</div>
		</div>

	</div>

	<jsp:include page="../include/footer.jsp"></jsp:include>