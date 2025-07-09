<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- footer -->
<footer id="footer">
    <div class="inner">
        <div class="footer-box">
            <p class="desc monument">Everybody deserves<br>a second chance</p>
            <div class="copyright">
                <p>Copyright ©쌍용교육센터7강의실 1팀 All rights reserved.</p>
            </div>
        </div>
    </div>
</footer>
<!-- //footer -->
<%-- 여기서 root 경로는 static 입니다.  --%>
<c:if test="${not empty requestScope.cart}">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</c:if>
<c:if test="${empty requestScope.cart}">
    <script src="${pageContext.request.contextPath}/lib/bootstrap-4.6.2-dist/js/bootstrap.bundle.js"></script>
</c:if>
<script src="${pageContext.request.contextPath}/js/common/common.js" defer></script>
<div id="loadingBar">
    <div class="loading-box">
        <div class="spinner"></div>
        <div class="text">이메일 발송 중 입니다.</div>
    </div>
</div>

</div>
</body>
</html>