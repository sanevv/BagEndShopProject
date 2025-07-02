<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Header --%>
<jsp:include page="../include/header.jsp" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review/review.css">

<!-- main contents -->
<main id="main">
    <div id="reviewForm">
        <div id="reviewDetail" class="review-container">
            <div class="review-header">
                <h2>REVIEW 상세보기</h2>
            </div>
            <div class="review-body">
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
                                <div>${reviewVO.reviewContents}</div>
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
                            <th>첨부된 이미지</th>
                            <td>
                                <c:if test="${not empty reviewVO.reviewImagePath}">
                                <img src="${pageContext.request.contextPath}${reviewVO.reviewImagePath}" alt="첨부된 이미지" />
                                </c:if>
                                <c:if test="${empty reviewVO.reviewImagePath}">
                                    등록된 이미지가 없다!
                                </c:if>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <%-- 관리자 답변 --%>
                <div id="adminComments"></div>

                <button type="button" class="btn-review-write" onclick="history.back()">목록으로 돌아가기</button>
            </div>
        </div>
    </div>

    <input type="hidden" id="rating" value="${reviewVO.rating}">
</main>
<!-- //main contents -->

<script>
    $(function(){

        const $starBox = $(".star-box");
        const ratingVal = $("#rating").val();

        if( Number(ratingVal) === 0 ) return;

        $starBox.eq(Number(ratingVal - 1)).trigger("click");


        reviewCommentList();
    });

    reviewCommentList = () => {

        const adminComments = document.querySelector('#adminComments');


        fetch(`/api/comment/list?reviewId=${reviewId}`)
            .then(response => response.json())
            .then(data => {
                console.log(data.isComment);
                if( !data.isComment ) {
                    adminComments.style.display = "none";
                    return;
                }
                adminComments.innerHTML = '<h3 class="title">관리자 답변</h3>' +
                                          '<p class="admin-contents">' + data.commentContents + '</p>';

            })

            .catch(error => console.error("API 호출 실패:", error));

    }
</script>


<%-- Footer --%>
<jsp:include page="../include/footer.jsp" />
