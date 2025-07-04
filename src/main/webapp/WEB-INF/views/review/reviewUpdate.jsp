<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Header --%>
<jsp:include page="../include/header.jsp" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review/review.css">
<script src="<%=request.getContextPath()%>/js/review/reviewUpdate.js" defer></script>

<!-- main contents -->
<main id="main">
    <div id="reviewForm">
        <div id="reviewUpdate" class="review-container">
            <div class="review-header">
                <h2>REVIEW</h2>
            </div>
            <div class="review-body">
                <form id="reviewUpdateForm" name="reviewUpdateForm">
                    <input type="hidden" name="reviewId" value="${reviewId}">
                    <input type="hidden" name="productId" value="${productId}">
                    <input type="hidden" name="userId" value="${userId}">
                    <input type="hidden" id="rating" name="rating" value="${reviewVO.rating}">

                    <table class="review-table">
                        <colgroup>
                            <col style="width: 200px">
                            <col style="width: auto">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>구매한 상품</th>
                                <td>
                                    <div class="image">
                                        <img src="${pageContext.request.contextPath}${productImagePath}" onerror="this.src='/images/error/zz.png' " alt="내가 주문한 상품의 대표 이미지">
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td>
                                    <textarea name="reviewContents">${reviewVO.reviewContents}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <th>평점</th>
                                <td>
                                    <div class="star">
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 1개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 1.5개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 2개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 2.5개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 3개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 3.5개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 4개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 4.5개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 5개</span></span>
                                        </label>
                                        <label class="star-box">
                                            <input type="radio" name="star" id="">
                                            <span class="star-img"><span class="blind">별 5.5개</span></span>
                                        </label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>첨부파일</th>
                                <td>
                                    <input type="file" name="reviewImageFile">
                                </td>
                            </tr>
                            <c:if test="${not empty reviewVO.reviewImagePath}">
                                <tr>
                                    <th>첨부된 이미지</th>
                                    <td>
                                        <img src="${pageContext.request.contextPath}${reviewVO.reviewImagePath}" alt="첨부된 이미지" />
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    <button type="button" id="btnUpdateReview" class="btn-review-write" onclick="reviewUpdate()">리뷰 수정하기</button>
                </form>
            </div>
        </div>
    </div>
</main>
<!-- //main contents -->

<script>
    $(function(){

        const $starBox = $(".star-box");
        const ratingVal = $("#rating").val();

        if( Number(ratingVal) === 0 ) return;

        $starBox.eq(Number(ratingVal - 1)).trigger("click");

    });
</script>

<%-- Footer --%>
<jsp:include page="../include/footer.jsp" />
