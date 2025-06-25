<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
<%-- Header --%>
<jsp:include page="include/header.jsp" />

<!-- main contents -->
<main id="main">
    <div class="main-banner swiper">
        <div id="mainBanner" class="swiper-wrapper"></div>
        <div class="swiper-navigation">
            <button type="button" class="swiper-button-prev"></button>
            <button type="button" class="swiper-button-next"></button>
        </div>
    </div>
</main>
<!-- //main contents -->

<%-- Header --%>
<jsp:include page="include/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main/main.js"></script>