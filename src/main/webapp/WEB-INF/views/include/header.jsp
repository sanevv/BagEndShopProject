<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // 로그인 여부 확인
    boolean isLoginJava = session.getAttribute("loginUser") != null;
%>
<!DOCTYPE html>
<html>
<head>
    <title>시후와아이들</title>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
    <c:if test="${not empty requestScope.cart}">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </c:if>
    <c:if test="${empty requestScope.cart}">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap-4.6.2-dist/css/bootstrap.css">
    </c:if>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/common.css">

    <script src="${pageContext.request.contextPath}/lib/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

    <div id="wrap">
        <!-- header -->
        <header id="header">
            <%--
                ***** 마크업 수정 예정입니다 *****
                ***** 임시 페이지입니다. *****
            --%>

            <%-- admin 로그인하면 노출할 예정입니다. --%>
            <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.roleId == 1}">
                <div class="admin-login">ADMIN 로그인 중</div>
            </c:if>
            <%-- //admin 로그인하면 노출할 예정입니다. --%>
            <div class="header-container">
                <div class="inner">
                    <div class="menu-navi navi-list">
                        <button id="btnAllMenu">
                            <span></span>
                            <span></span>
                            <span></span>
                        </button>
                        <div class="navi-item">
                            <c:if test="${empty sessionScope.loginUser}">
                                <a href="${pageContext.request.contextPath}/test/login.up" id="login-status">
                                    <img src="${pageContext.request.contextPath}/images/common/icon/icon_header_account.svg"
                                         alt="마이페이지 아이콘"/>
                                </a>
                            </c:if>
                            <c:if test="${not empty sessionScope.loginUser}">
                                <a href="${pageContext.request.contextPath}/orderShow/orderDetails" id="login-status">
                                    <img src="${pageContext.request.contextPath}/images/common/icon/icon_header_account.svg"
                                         alt="마이페이지 아이콘"/>
                                </a>
                            </c:if>
                        </div>
                    </div>
                    <div class="logo">
                        <a href="/">BAGEND</a>
                    </div>
                    <div class="side-navi navi-list">
                        <div class="navi-item search-item">
                            <button type="button" class="btn-search"><img
                                    src="${pageContext.request.contextPath}/images/common/icon/icon_header_search.svg"
                                    alt="" class="max"></button>
                            <div class="search-form">
                                <form>
                                    <input type="search" class="inp-search" placeholder="검색어를 입력해주세요"/>
                                </form>
                            </div>
                        </div>
                        <div class="navi-item">
                            <a href="${pageContext.request.contextPath}/cart">
                                <span class="count cart-count">
                                    <c:if test="<%= !isLoginJava %>">
                                        <span class="basket-count">0</span>
                                    </c:if>
                                    <c:if test="<%= isLoginJava %>">
                                        <span class="basket-count"></span>
                                    </c:if>

                                </span>
                            </a>
                        </div>
                    </div>
                </div>
                <div id="allMenu">
                    <div class="inner">
                        <ul>
                            <li><a href="productList.one">BAG</a></li>
                            <li><a href="productList.one">ACC</a></li>
                            <li><a href="${pageContext.request.contextPath}/product/list">시후냉동바보</a></li>
                            <li><a href="${pageContext.request.contextPath}/notice/list.one">승호바보</a></li>
                            <li><a href="productList.one">경수바보</a></li>
                            <li><a href="productList.one">애리천재</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- //header -->


        </header>
        <!-- //header -->
        <script defer>//
            const isLogin = <%= isLoginJava %>;
            function showCartCount() {
                axios.get('/api/cart/count')
                    .then(response => {
                        const count = response.data;
                        const cartCountElement = document.querySelector('.basket-count');
                        if (cartCountElement)
                            cartCountElement.textContent = count > 0 ? count : 0;

                    })
                    .catch(error => {
                        console.error('Error fetching cart count:', error);
                    })
            }

            if(isLogin)
                showCartCount();
        </script>
