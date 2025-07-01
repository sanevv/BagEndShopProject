<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<!--  Notice -->
<div class="container-fluid">
  
  	<div class="text-center" style="font-size: 20pt; font-weight: bold; margin: 50px 0;">
		NOTICE
	</div>
  
  <div class="row">
	
	<c:forEach var="nvo" items="${nvoList}">
	    <div class="col-md-3">
	        <a href="${pageContext.request.contextPath}/notice/detail.one?notice_id=${nvo.notice_id}" class="notice-link">
	            <div class="image-card" style="background-image: url('${pageContext.request.contextPath}${nvo.thumbnail}');">
	                <span class="image-title">${nvo.title}</span>
	            </div>
	        </a>
	    </div>
	</c:forEach>


  </div>
</div>
<!--  Notice -->

<%-- Header --%>
<jsp:include page="include/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main/main.js"></script>
<script src="${pageContext.request.contextPath}/IMG_20250217_131537.jpg"></script>