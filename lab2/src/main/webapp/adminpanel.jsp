<%@ page import="bsuir.dtalalaev.lab2.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

        .username-link {
            color: #007bff;
            text-decoration: underline;
            cursor: pointer;
        }
        .block-button {
            background-color: #d9534f; /* Bootstrap's danger color */
            color: white;
            padding: 8px 12px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 5px;
            border-radius: 4px;
            cursor: pointer;
        }

        /* Styles for Unblock button */
        .unblock-button {
            background-color: #5cb85c; /* Bootstrap's success color */
            color: white;
            padding: 8px 12px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 5px;
            border-radius: 4px;
            cursor: pointer;
        }

        /* Styles for Promote button */
        .promote-button {
            background-color: #ffd700; /* Gold color */
            color: white;
            padding: 8px 12px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 5px;
            border-radius: 4px;
            cursor: pointer;
        }

        /* Styles for Demote button */
        .demote-button {
            background-color: #000000; /* Black color */
            color: #d9534f; /* Bootstrap's danger color */
            padding: 8px 12px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 5px;
            border-radius: 4px;
            cursor: pointer;
        }

        /* Styles for Block button */
        .block-button:hover {
            background-color: #c9302c; /* Darker shade of red on hover */
        }

        /* Styles for Unblock button */
        .unblock-button:hover {
            background-color: #4cae4c; /* Darker shade of green on hover */
        }

        /* Styles for Promote button */
        .promote-button:hover {
            background-color: #e5a00d; /* Darker shade of gold on hover */
        }

        /* Styles for Demote button */
        .demote-button:hover {
            background-color: #1e1e1e; /* Darker shade of black on hover */
        }

    </style>

</head>
<body>

<c:if test="${not empty reload}">
    <script>
        location.reload(true);
    </script>
</c:if>

<c:if test="${isadmin}">
    <div>
        <form action="${pageContext.request.contextPath}/loadshop" method="post" style="display: inline;">
            <input type="submit" value="Back to shop" class="back-button">
        </form>
        <form action="${pageContext.request.contextPath}/products" method="post" style="display: inline;">
            <input type="submit" value="List of Products" class="back-button">
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

        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>
                    <form id="userForm${user.userId}" action="${pageContext.request.contextPath}/loadcart/${user.userId}" method="post">
                        <input type="hidden" name="postAction" value="true">
                        <a class="username-link" href="#" onclick="document.getElementById('userForm${user.userId}').submit(); return false;">${user.userName}</a>
                    </form>
                </td>
                <td>${user.userLogin}</td>
                <td>${user.admin}</td>
                <td>${user.blocked}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/adminAction/${user.userId}" method="post">
                        <input type="hidden" name="userId" value="${user.userId}">

                        <c:set var="blockButtonValue" value="${user.blocked ? 'Unblock' : 'Block'}"/>
                        <c:set var="promoteButtonValue" value="${user.admin ? 'Demote' : 'Promote'}"/>

                        <input type="submit" name="action" value="${blockButtonValue}" class="${user.blocked ? 'unblock-button' : 'block-button'}">
                        <input type="submit" name="action" value="${promoteButtonValue}" class="${user.admin ? 'demote-button' : 'promote-button'}">
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>
</c:if>

<c:if test="${not isadmin}">
    <h1>Access Denied</h1>
    <form action="${pageContext.request.contextPath}/loadshop" method="post" style="display: inline;">
        <input type="submit" value="Back to shop" class="back-button">
    </form>
</c:if>

</body>
</html>
