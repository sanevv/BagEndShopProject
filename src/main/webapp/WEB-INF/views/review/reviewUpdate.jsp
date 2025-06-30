<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Header --%>
<jsp:include page="../include/header.jsp" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review/review.css">
<script src="<%=request.getContextPath()%>/js/review/review.js" defer></script>

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
                    <input type="hidden" name="rating" value="${rating}">
<%--                    <input type="hidden" name="productImagePath" value="${productImagePath}">--%>
<%--                    <input type="hidden" name="reviewContents" value="">--%>

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
                                    <img src="${pageContext.request.contextPath}${productImagePath}" alt="내가 주문한 상품의 대표 이미지">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td>
                                <textarea name="reviewContents">${reviewContents}</textarea>
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
                                <input type="file" name="attachFile">
                            </td>
                        </tr>
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
    const starBox = document.querySelector('.star-box');

    starBox.addEventListener('click', function(e) {
        this.querySelector('input').checked = true;
    })
</script>



<%-- Footer --%>
<jsp:include page="../include/footer.jsp" />
