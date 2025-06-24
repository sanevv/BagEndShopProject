<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>시후와아이들</title>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap-4.6.2-dist/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/common.css">
    <%--<script src="${pageContext.request.contextPath}/lib/jquery-3.7.1.min.js"></script>--%>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <script src="${pageContext.request.contextPath}/lib/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

    <script type="text/javascript">

        $(function (){

        })// end of $(function (){}

        // 로그인 유무로 내 정보 눌렀을때 로그인 페이지로 이동 혹은 내 정보 페이지로 가는 함수
        function goLoginOrOtherMenusImgtag() {

            if(${empty sessionScope.loginUser}){
                location.href = "${requestScope.contextPath}/test/login.up";
            }
            else{
                location.href = "";
            }
        } //

        // 로그인 유무로 장바구니 눌렀을때 로그인 페이지로 이동 혹은 장바구니 페이지로 가는 함수
        function goLoginOrOtherMenusSpantag() {

            if(${empty sessionScope.loginUser}){
                location.href = "${requestScope.contextPath}/test/login.up";
            }
            else{
                location.href = "";
            }
        }



    </script>

    <div id="wrap">
        <!-- header -->
        <header id="header">
            <%--
                ***** 마크업 수정 예정입니다 *****
                ***** 임시 페이지입니다. *****
            --%>

            <%-- admin 로그인하면 노출할 예정입니다. --%>
            <div class="admin-login">ADMIN 로그인 중</div>
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
                            <a href="javascript:;" id="login-status">
                                <img src="${pageContext.request.contextPath}/images/common/icon/icon_header_account.svg"
                                     alt="마이페이지 아이콘" onclick="goLoginOrOtherMenusImgtag()"/>
                            </a>
                        </div>
                    </div>
                    <div class="logo">
                        <a href="index.html">BAGEND</a>
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
                            <a href="javascript:;">
                                <span class="count cart-count"><span class="basket-count" onclick="goLoginOrOtherMenusSpantag()">0</span></span>
                            </a>
                        </div>
                    </div>
                </div>
                <div id="allMenu">
                    <div class="inner">
                        <ul>
                            <li><a href="javascript:;">BAG</a></li>
                            <li><a href="javascript:;">ACC</a></li>
                            <li><a href="javascript:;">시후바보</a></li>
                            <li><a href="javascript:;">승호바보</a></li>
                            <li><a href="javascript:;">경수바보</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </header>
        <!-- //header -->
