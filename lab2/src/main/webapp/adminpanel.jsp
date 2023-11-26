<%@ page import="bsuir.dtalalaev.lab2.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management</title>
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

        .username-link {
            color: #007bff;
            text-decoration: underline;
            cursor: pointer;
        }
    </style>
</head>
<body>
<%
    boolean reload = false;
    if(request.getSession().getAttribute("reload") != null)
    {
        request.getSession().setAttribute("reload", null);
%>
<script>
    location.reload(true);
</script>
<%
    }

    boolean isadmin = (Boolean) request.getSession().getAttribute("isadmin");
    if (isadmin) {
        List<User> userList = (List<User>) request.getSession().getAttribute("users");

%>
<div>
    <a class="back-button" href="shop.jsp">Back to Shop</a>
    <form action="<%= request.getContextPath() %>/products" method="post" style="display: inline;">
        <input type="submit" value="List of Products" class="products-button">
    </form>
</div>
<table>
    <tr>
        <th>User ID</th>
        <th>User Name</th>
        <th>User Login</th>
        <th>Is Admin</th>
        <th>Is Blocked</th>
        <th>Action</th>
    </tr>
    <%
        for (User user : userList) {
    %>
    <tr>
        <td><%= user.getUserId() %></td>
        <td><a class="username-link" href="<%= request.getContextPath() %>/loadcart/<%= user.getUserId() %>"><%= user.getUserName() %></a></td>
        <td><%= user.getUserLogin() %></td>
        <td><%= user.isAdmin() %></td>
        <td><%= user.isBlocked() %></td>
        <td>
            <form action="<%= request.getContextPath() %>/adminAction/<%= user.getUserId() %>" method="post">
                <input type="hidden" name="userId" value="<%= user.getUserId() %>">
                <input type="submit" name="action" value="<%= user.isAdmin()?"Demote":"Promote"%>">
                <input type="submit" name="action" value="<%=user.isBlocked()?"Unblock":"Block"%>">
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
