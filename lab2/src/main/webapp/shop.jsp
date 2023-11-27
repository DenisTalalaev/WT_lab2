<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Base64" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Product Page</title>


    <style>
        /* Стили для всплывающего окна */
        .popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            background-color: #ffffff;
            border: 1px solid #cccccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            z-index: 1000;
        }
    </style>


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
            margin: auto;
            margin-bottom: 10px;
            align-content: center;
            align-items: center;
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
        .button-container form button {
            background-color: #555; /* Button color */
            color: #fff; /* Text color */
            border: none;
            padding: 8px 15px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .button-container form button:hover {
            background-color: #333; /* Button color on hover */
        }

        /* Style for the Cart button */
        .button-container form[action="loadUserCart"] button {
            background-color: #4CAF50; /* Cart button color */
        }

        /* Style for the Logout button */
        .button-container form[action="logout"] button {
            background-color: #f44336; /* Logout button color */
        }

        /* Style for the AdminPanel button */
        .button-container form[action="adminpanel"] button {
            background-color: #2196F3; /* AdminPanel button color */
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

        <c:if test="${sessionScope.isadmin}">
            <form method="post" action="adminpanel">
                <button type="submit">AdminPanel</button>
            </form>
        </c:if>
    </div>
</div>

<c:choose>
    <c:when test="${empty sessionScope.userid}">
        <c:redirect url="/login.jsp"/>
    </c:when>
    <c:when test="${sessionScope.isblocked}">
        <h1>На вас наложили великую печать бана.</h1>
    </c:when>
    <c:otherwise>

        <h2 style="color: #4CAF50; text-align: center;">Products</h2>

        <div class="product-container">
            <c:forEach var="product" items="${sessionScope.products}">
                <div class="product-block">
                    <img src="data:image/png;base64, ${Base64.getEncoder().encodeToString(product.productImage)}" alt="${product.productName}">
                    <h3>${product.productName}</h3>
                    <p>${product.productDescription}</p>
                    <p class="price">$${product.productPrice}</p>

                    <form method="post" action="addToCart">
                        <input type="hidden" name="userId" value="${sessionScope.userid}">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <label for="quantity">Quantity:</label>
                        <input type="number" id="quantity" name="quantity" min="1" value="1" required>
                        <button type="submit" class="add-to-cart-form">Add to Cart</button>
                    </form>
                </div>
            </c:forEach>
        </div>

    </c:otherwise>
</c:choose>

</body>
</html>
