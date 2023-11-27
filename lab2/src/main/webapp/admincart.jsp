<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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

<c:set var="usercart" value="${sessionScope.usercart}"/>
<c:set var="username" value="${sessionScope.username}"/>

<div>
    <a class="back-button" href="${pageContext.request.contextPath}/adminpanel.jsp">Back to Admin Panel</a>
    <span>Username: ${username}</span>
</div>

<table>
    <tr>
        <th>Cart ID</th>
        <th>User ID</th>
        <th>Product</th>
        <th>Count</th>
        <th>Action</th>
    </tr>

    <c:forEach var="cartItem" items="${usercart}">
        <tr>
            <td>${cartItem.cartId}</td>
            <td>${cartItem.userId}</td>
            <td style="display: flex; align-items: center;">
                <img style="max-width: 100px; margin-right: 10px;"
                     src="data:image/png;base64,${Base64.getEncoder().encodeToString(cartItem.product.productImage)}"
                     alt="${cartItem.product.productName}">
                <div>
                    <p><strong>${cartItem.product.productName}</strong></p>
                    <p>${cartItem.product.productDescription}</p>
                </div>
            </td>
            <td>${cartItem.count}</td>
            <td>
                <form action="${pageContext.request.contextPath}/deleteCart/${cartItem.cartId}" method="post">
                    <input type="submit" value="Delete" class="delete-button">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
