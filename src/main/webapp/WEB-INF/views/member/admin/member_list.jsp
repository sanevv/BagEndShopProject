<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 13.
  Time: 오전 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../../header2.jsp"/>
<style>
    table#memberTbl {
        width: 80%;
        margin: 0 auto;
    }

    table#memberTbl th {
        text-align: center;
        font-size: 14pt;
    }

    table#memberTbl tr.memberInfo:hover {
        background-color: #e6ffe6;
        cursor: pointer;
    }

    form[name="member_search_frm"] {
        border: solid 0px red;
        width: 80%;
        margin: 0 auto 3% auto;
    }

    form[name="member_search_frm"] button.btn-secondary {
        margin-left: 2%;
        margin-right: 32%;
    }

</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin/memberList.js" defer></script>
<script type="text/javascript" defer>

    $(() => {

    })//end of ()=>






    // function startGetData(page, size, searchValue, searchType) {
    //     axios.get
    //
    //
    // }
    // startGetData()





    $('select[name="sizePerPage"]').on('change', () => {
        const frm = document.member_search_frm;
        frm.action = "memberList.up"
        // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
        frm.submit();
    })




</script>
<div class="container" style="padding: 3% 0;">
    <h2 class="text-center mb-5">::: 회원전체 목록 :::</h2>
    <form name="member_search_frm">
        <select name="searchType" id="searchType">
            <option value="">검색대상</option>
            <option value="userName">회원명</option>
            <option value="userId">아이디</option>
            <option value="email">이메일</option>
        </select> &nbsp; <input type="text" name="searchWord" id="searchValue" />

        <input type="text" style="display: none;" />
        <%-- 조심할 것은 type="hidden" 이 아니다. --%>

        <button type="button" class="btn btn-secondary" onclick="goSearch()">검색</button>

        <span style="font-size: 12pt; font-weight: bold;">페이지당 회원명수&nbsp;-&nbsp;</span>

        <select name="sizePerPage">
            <option value="10">10명</option>
            <option value="5">5명</option>
            <option value="3">3명</option>
        </select>
    </form>


    <table class="table table-bordered" id="memberTbl">
        <thead>
        <tr>
            <th>번호</th>
            <th>아이디</th>
            <th>회원명</th>
            <th>이메일</th>
            <th>성별</th>
        </tr>
        </thead>

        <tbody id="searchTbody">
<%--        <c:if test="${not empty requestScope.memberList}">--%>
<%--            <c:forEach var="response" items="${requestScope.memberList}" varStatus="status">--%>
<%--                <tr class="memberInfo">--%>
<%--                    <td>--%>
<%--                            ${response.searchCount}--%>
<%--                    </td>--%>
<%--                    <td>--%>
<%--                            ${response.userId}--%>
<%--                    </td>--%>
<%--                    <td>--%>
<%--                            ${response.userName}--%>
<%--                    </td>--%>
<%--                    <td>--%>
<%--                            ${response.email}--%>
<%--                    </td>--%>
<%--                    <td>--%>
<%--                            ${response.gender}--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--        </c:if>--%>
<%--        <c:if test="${empty requestScope.memberList}">--%>
<%--            <tr>--%>
<%--                <td colspan="5">--%>
<%--                    데이터가 존재하지 않습니다.--%>
<%--                </td>--%>
<%--            </tr>--%>

<%--        </c:if>--%>


        </tbody>
    </table>

</div>
<jsp:include page="../../footer2.jsp"/>