<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bsuir.dtalalaev.lab2.locale.MessageManager" %>
<%@ page import="bsuir.dtalalaev.lab2.entities.Product" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Product Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #333; /* Цвет шапки */
            padding: 10px;
        }

        .logo {
            font-size: 24px;
            color: #fff; /* Цвет текста в шапке */
            margin-right: 20px;
        }

        .button-container {
            display: flex;
        }

        .button-container form {
            margin-left: 10px;
        }

        .product-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
            padding: 20px;
        }

        .product-block {
            border: 1px solid #ddd;
            padding: 10px;
            margin: 10px;
            width: calc(33.33% - 20px); /* Три товара в ряд на всю ширину окна */
            background-color: #fff;
        }

        img {
            max-width: 100%;
            max-height: 150px;
            object-fit: cover;
            margin-bottom: 10px;
        }

        h3 {
            color: #333;
            font-size: 18px;
            margin-bottom: 5px;
        }

        p {
            color: #666;
            margin-bottom: 10px;
        }

        .price {
            color: #4CAF50; /* Цвет цены */
            font-size: 16px;
        }

        .add-to-cart-form {
            background-color: #4CAF50;
            color: #fff;
            border: none;
            padding: 8px;
            cursor: pointer;
        }

        .add-to-cart-form:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="header">
    <div class="logo">DShop</div>

    <div class="button-container">
        <form method="post" action="logout">
            <button type="submit">Logout</button>
        </form>

        <form method="post" action="loadUserCart">
            <button type="submit">Cart</button>
        </form>

        <% if ((Boolean) request.getSession().getAttribute("isadmin")) { %>
        <form method="post" action="adminpanel">
            <button type="submit">AdminPanel</button>
        </form>
        <% } %>
    </div>
</div>

<%
    boolean isBlocked = false;
    MessageManager message = (MessageManager) request.getAttribute(MessageManager.MESSAGE);
    MessageManager exception = (MessageManager) request.getAttribute(MessageManager.EXCEPTION);
    int userid = -1;
    if (request.getSession().getAttribute("userid") != null) {
        userid = Integer.parseInt((String) request.getSession().getAttribute("userid"));
    }
    if(request.getSession().getAttribute("isblocked") != null) {
        if((Boolean) request.getSession().getAttribute("isblocked")){
            isBlocked = true;
        }
    }
    if(userid == -1 && !isBlocked) {
        response.sendRedirect("/login.jsp");
    } else if (isBlocked) {
%>
<h1> На вас наложили великую печать бана.</h1>
<%
    }
%>

<% if (!isBlocked) { %>
<h2 style="color: #4CAF50; text-align: center;">Products</h2>

<div class="product-container">
    <%
        List<Product> products = (List<Product>) request.getSession().getAttribute("products");
        for (Product product : products) {
    %>
    <div class="product-block">
        <img src="<%= "data:image/png;base64, " + Base64.getEncoder().encodeToString(product.getProductImage()) %>" alt="<%= product.getProductName() %>">
        <h3><%= product.getProductName() %></h3>
        <p><%= product.getProductDescription() %></p>
        <p class="price">$<%= product.getProductPrice() %></p>

        <form method="post" action="addToCart">
            <input type="hidden" name="userId" value="<%= request.getSession().getAttribute("userid") %>">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="1" value="1" required>
            <button type="submit" class="add-to-cart-form">Add to Cart</button>
        </form>
    </div>
    <%
        }
    %>
</div>
<% } %>

</body>
</html>
