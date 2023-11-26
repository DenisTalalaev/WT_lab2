<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="bsuir.dtalalaev.lab2.entities.Cart" %>
<%@ page import="java.util.Base64" %>
<html>
<head>
    <title>Admin Cart Page</title>
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

        .back-button {
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
    </style>
</head>
<body>

<%
    List<Cart> usercart = (List<Cart>) request.getSession().getAttribute("usercart");
    String username = (String) request.getSession().getAttribute("username");
%>

<div>
    <a class="back-button" href="<%= request.getContextPath() %>/adminpanel.jsp">Back to Admin Panel</a>
    <span>Username: <%= username %></span>
</div>

<table>
    <tr>
        <th>Cart ID</th>
        <th>User ID</th>
        <th>Product</th>
        <th>Count</th>
        <th>Action</th>
    </tr>

    <%
        for (Cart cartItem : usercart) {
    %>
    <tr>
        <td><%= cartItem.getCartId() %></td>
        <td><%= cartItem.getUserId() %></td>
        <td style="display: flex; align-items: center;">
            <img style="max-width: 100px; margin-right: 10px;" src="<%= "data:image/png;base64, " + Base64.getEncoder().encodeToString(cartItem.getProduct().getProductImage()) %>" alt="<%= cartItem.getProduct().getProductName() %>">
            <div>
                <p><strong><%= cartItem.getProduct().getProductName() %></strong></p>
                <p><%= cartItem.getProduct().getProductDescription() %></p>
            </div>
        </td>

        <td><%= cartItem.getCount() %></td>
        <td>
            <form action="<%= request.getContextPath() %>/deleteCart/<%= cartItem.getCartId() %>" method="post">
                <input type="submit" value="Delete" class="delete-button">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>