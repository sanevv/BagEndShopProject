<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // 로그인 여부 확인
    boolean isLoginJava = session.getAttribute("loginUser") != null;
%>

<script type="text/javascript">

    const frm = document.prodRegisterFrm;
    if (frm) {
        frm.method = "post";
        frm.action = "prod/register";
        frm.submit();
    }

</script>
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
                            <button type="button" class="btn-search">
                                <img src="${pageContext.request.contextPath}/images/common/icon/icon_header_search.svg"
                                     alt="" class="max">
                            </button>
                            <div class="search-form">
                                <%--						        <form id="searchForm" onsubmit="return goSearch();">--%>
                                <input type="search" id="searchInput" name="keyword" class="inp-search"
                                       placeholder="검색어를 입력해주세요" autocomplete="off"/>
                                <label for="searchInput"></label>
                                <button type="button" id="searchBtn">
                                    <img src="${pageContext.request.contextPath}/images/common/icon/icon_header_search.svg"
                                         alt="" class="max">
                                </button>
                                <%--                                </form>--%>
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
                        <ul class="main-menu">
                            <li>
                                <a href="${pageContext.request.contextPath}/product/list">ALL</a>
                                <ul class="sub-menu">
                                    <li><a href="${pageContext.request.contextPath}/product/messenger">- MESSENGER</a>
                                    </li>
                                    <li><a href="${pageContext.request.contextPath}/product/cross">- CROSS</a></li>
                                    <li><a href="${pageContext.request.contextPath}/product/backpack">- BACKPACK</a>
                                    </li>
                                </ul>
                            </li>

                            <li><a href="${pageContext.request.contextPath}/notice/list.one">NOTICE</a></li>


                            <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.roleId == 1}">
                                <li>
                                    <form name="prodRegisterFrm"
                                          action="${pageContext.request.contextPath}/prod/register" method="post">
                                        <a>
                                            <button type="submit" style="all:unset; cursor:pointer;">제품 등록</button>
                                        </a>
                                    </form>
                                </li>
                            </c:if>
                            <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.roleId == 1}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/dashboard">최근 주문금액 통계 리스트</a>
                                </li>
                            </c:if>
                            <br>
                            <br>
                            <br>
                            <li><a class="submenu" href="#" style="font-size: 18px; color: #A3A9AF;">오시는길</a></li>

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

        const contextPath = '${pageContext.request.contextPath}';

        // function goSearch() {
        //     const keyword = document.getElementById('keyword').value.trim();
        //     if (!keyword) {
        //         alert("검색어를 입력해주세요.");
        //         return false;
        //     }
        //
        //     const path = contextPath.replace(/\/+$/, '');
        //     location.href = path + '/productSearch?search=' + encodeURIComponent(keyword);
        //     return false;
        // }

        //검색
        const searchInput = document.getElementById('searchInput');
        const searchBtn = document.getElementById('searchBtn');
        searchInput.addEventListener("keyup", function (event) {
            if (event.key === "Enter") {
                productPageMovement(searchInput.value);
            }
        });
        productPageMovement = (searchKeyword) => {
            if (searchKeyword) {
                console.log(searchKeyword);
                location.href = `${pageContext.request.contextPath}/product?search=\${encodeURIComponent(searchKeyword)}`;
                return;
            }
            alert("검색어를 입력해주세요.");
        }
        searchBtn.addEventListener("click", function () {
            productPageMovement(searchInput.value);
        })


        </script>
