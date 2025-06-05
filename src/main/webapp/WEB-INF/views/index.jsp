<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String ctxPath = request.getContextPath();
%>


<jsp:include page="header1.jsp" />

<div class="container">
  <div id="carouselExampleIndicators" class="carousel slide"
       data-ride="carousel">
    <ol class="carousel-indicators">

      <!-- <li data-target="#carouselExampleIndicators" data-slide-to="0"
         class="active"></li>
      <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
      <li data-target="#carouselExampleIndicators" data-slide-to="2"></li> -->

      <c:if test="${not empty imgList}">
        <c:forEach items="${imgList}" varStatus="status">
          <c:if test="${status.index == 0}">
            <li data-target="#carouselExampleIndicators"
                data-slide-to="${status.index}" class="active"></li>
          </c:if>
          <c:if test="${status.index > 0}">
            <li data-target="#carouselExampleIndicators"
                data-slide-to="${status.index}"></li>
          </c:if>
        </c:forEach>

      </c:if>


    </ol>
    <div class="carousel-inner">

      <%--    <div class="carousel-item active">
         <img src="<%= ctxPath %>/images/동원.png" class="d-block w-100" alt="...">
         <!-- d-block 은 display: block; 이고  w-100 은 width 의 크기는 <div class="carousel-item active">의 width 100% 로 잡으라는 것이다. -->
         <div class="carousel-caption d-none d-md-block">
            <!-- d-none 은 display : none; 이므로 화면에 보이지 않다가, d-md-block 이므로 d-md-block 은 width 가 768px이상인 것에서만 display: block; 으로 보여라는 말이다.  -->
            <h5>동원</h5>
         </div>
      </div>
      <div class="carousel-item">
         <img src="<%= ctxPath %>/images/레노보.png" class="d-block w-100" alt="...">
         <div class="carousel-caption d-none d-md-block">
            <h5>레노보</h5>
         </div>
      </div>
      <div class="carousel-item">
         <img src="<%= ctxPath %>/images/미샤.png" class="d-block w-100" alt="...">
         <div class="carousel-caption d-none d-md-block">
            <h5>미샤</h5>
         </div>
      </div> --%>

      <c:if test="${not empty imgList}">
        <c:forEach var="imageVO" items="${imgList}" varStatus="status">
          <c:if test="${status.index == 0}">
            <div class="carousel-item active">
              <img src="<%=ctxPath%>/images/${imageVO.imgfilename}"
                   class="d-block w-100" alt="...">
              <div class="carousel-caption d-none d-md-block">
                <h5>${imageVO.imgname}</h5>
              </div>
            </div>
          </c:if>

          <c:if test="${status.index > 0}">
            <div class="carousel-item">
              <img src="<%=ctxPath%>/images/${imageVO.imgfilename}"
                   class="d-block w-100" alt="...">
              <div class="carousel-caption d-none d-md-block">
                <h5>${imageVO.imgname}</h5>
              </div>
            </div>
          </c:if>

        </c:forEach>
      </c:if>

    </div>
    <a class="carousel-control-prev" href="#carouselExampleIndicators"
       role="button" data-slide="prev"> <span
            class="carousel-control-prev-icon" aria-hidden="true"></span> <span
            class="sr-only">Previous</span>
    </a> <a class="carousel-control-next" href="#carouselExampleIndicators"
            role="button" data-slide="next"> <span
          class="carousel-control-next-icon" aria-hidden="true"></span> <span
          class="sr-only">Next</span>
  </a>
  </div>
</div>
<jsp:include page="footer1.jsp" />