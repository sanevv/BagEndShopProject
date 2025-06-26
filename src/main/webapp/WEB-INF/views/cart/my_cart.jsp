<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 26.
  Time: 오후 5:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../include/header.jsp"/>

<style>
  body {
    font-family: 'Segoe UI', sans-serif;
    background-color: #f5f5f5;
    margin: 0;
    padding: 30px;
  }

  h1 {
    text-align: center;
    margin-bottom: 30px;
  }

  .cart-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
    max-width: 1200px;
    margin: 0 auto;
  }

  .cart-item {
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    transition: transform 0.2s;
  }

  .cart-item:hover {
    transform: translateY(-5px);
  }

  .cart-item img {
    width: 100%;
    height: 180px;
    object-fit: cover;
  }

  .cart-item-details {
    padding: 15px;
  }

  .cart-item-details h3 {
    margin: 0 0 10px;
    font-size: 18px;
  }

  .cart-item-details p {
    margin: 5px 0;
    color: #555;
  }
</style>

<h1>🛒 장바구니 목록</h1>
<div class="cart-container" id="cartContainer">
  <!-- 장바구니 항목들이 여기에 렌더링됩니다 -->
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/cart/myCart.js" defer></script>



<jsp:include page="../include/footer.jsp" />

