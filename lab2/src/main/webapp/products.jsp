<%@ page import="bsuir.dtalalaev.lab2.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Management</title>
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

        .back-button, .products-button {
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

        .add-product-form {
            margin-top: 20px;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .add-product-form label {
            display: block;
            margin-bottom: 8px;
        }

        .add-product-form input, .add-product-form textarea, .add-product-form select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
            border-radius: 4px;
        }

        .add-product-form input[type="file"] {
            margin-top: 10px;
        }

        .add-product-form input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 15px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .add-product-form input[type="submit"]:hover {
            background-color: #45a049;
        }

        .product-image {
            max-width: 100px;
            max-height: 100px;
        }
    </style>


</head>
<body>
<%
    boolean reload = false;
    if (request.getSession().getAttribute("reload") != null) {
        request.getSession().setAttribute("reload", null);
%>
<script>
    location.reload(true);
</script>
<%
    }

    boolean isAdmin = (Boolean) request.getSession().getAttribute("isadmin");
    if (isAdmin) {
        List<Product> productList = (List<Product>) request.getSession().getAttribute("products");

%>
<div>
    <a class="back-button" href="adminpanel.jsp">Back to Admin Panel</a>
</div>

<h1>Product Management</h1>

<div class="add-product-form">
    <form action="<%= request.getContextPath() %>/addProduct" method="post" enctype="multipart/form-data">
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required>

        <label for="productDescription">Product Description:</label>
        <textarea id="productDescription" name="productDescription" required></textarea>

        <label for="productPrice">Product Price:</label>
        <input type="number" id="productPrice" name="productPrice" step="0.01" required>

        <label for="productCount">Product Count:</label>
        <input type="number" id="productCount" name="productCount" required>

        <label for="productImage">Product Image:</label>
        <input type="file" id="productImage" name="productImage" accept="image/*" required>

        <input type="submit" value="Add Product">
    </form>
</div>


<table>
    <tr>
        <th>Product ID</th>
        <th>Product Name</th>
        <th>Product Description</th>
        <th>Product Price</th>
        <th>Product Count</th>
        <th>Product Image</th>
        <th>Action</th>
    </tr>
    <%
        for (Product product : productList) {
            // Получение данных изображения в виде массива байт
            byte[] imageData = product.getProductImage();

            // Преобразование массива байт в Base64
            String base64Image = Base64.getEncoder().encodeToString(imageData);
    %>
    <tr>
        <td><%= product.getProductId() %></td>
        <td><%= product.getProductName() %></td>
        <td><%= product.getProductDescription() %></td>
        <td><%= product.getProductPrice() %></td>
        <td><%= product.getProductCount() %></td>
        <td><img src="data:image/jpeg;base64,<%= base64Image %>" alt="Product Image" class="product-image"></td>
        <td>
            <form action="<%= request.getContextPath() %>/deleteProduct" method="post">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <input type="submit" value="Delete">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

<%
} else {
%>
<h1>Access Denied</h1>
<%
    }
%>

</body>
</html>
