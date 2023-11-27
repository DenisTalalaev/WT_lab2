<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="bsuir.dtalalaev.lab2.entities.Cart" %>
<%@ page import="java.util.Base64" %>
<%@ page import="bsuir.dtalalaev.lab2.entities.Product" %>
<html>
<head>
    <title>User Cart Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        h1 {
            color: #4CAF50;
        }

        .back-button, .pay-button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin-top: 20px;
            cursor: pointer;
        }

        .delete-button {
            background-color: #f44336;
            color: white;
            padding: 5px 10px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 12px;
            cursor: pointer;
        }

        .confirm-button {
            background-color: #4CAF50;
            color: white;
            padding: 5px 10px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 12px;
            cursor: pointer;
        }

        input[type=number] {
            width: 50px;
        }
    </style>
</head>
<body>

<div>
    <a class="back-button" href="<%= request.getContextPath() %>/shop.jsp">Back to Shop</a>
    <span>Total Price: <!-- Здесь может быть ваш расчет общей суммы --></span>
    <a class="pay-button" href="#">Pay</a>
</div>
<table>
    <tr>
        <th>Product</th>
        <th>Count</th>
        <th>Price</th>
        <th>Total Price</th>
        <th>Action</th>
    </tr>

    <%
        List<Cart> userCart = (List<Cart>) request.getSession().getAttribute("cartList");
        if (userCart != null) {
            for (Cart cartItem : userCart) {
                Product product = cartItem.getProduct();
                double productPrice = product.getProductPrice();
                int itemCount = cartItem.getCount();
                double totalPrice = productPrice * itemCount;
    %>
    <tr>
        <td style="display: flex; align-items: center;">
            <img style="max-width: 100px; margin-right: 10px;"
                 src="<%= "data:image/png;base64, " + Base64.getEncoder().encodeToString(product.getProductImage()) %>"
                 alt="<%= product.getProductName() %>">
            <div>
                <p><strong><%= product.getProductName() %></strong></p>
                <p><%= product.getProductDescription() %></p>
                <p>Price: <%= productPrice %></p>
            </div>
        </td>

        <td>
            <form action="<%= request.getContextPath() %>/updateCartCol" method="post">
                <input type="hidden" name="cartId" value="<%= cartItem.getCartId() %>">
                <input type="number" name="newCount" value="<%= itemCount %>" min="0">
                <input type="submit" value="Confirm" class="confirm-button">
            </form>
        </td>

        <td><%= productPrice %></td>

        <td><%= totalPrice %></td>

        <td>
            <form action="<%= request.getContextPath() %>/deleteCartUser/<%= cartItem.getCartId() %>" method="post">
                <input type="submit" value="Delete" class="delete-button">
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <h1>Empty cart</h1>
    <%
        }
    %>
</table>

</body>
</html>