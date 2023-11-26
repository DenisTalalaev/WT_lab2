<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bsuir.dtalalaev.lab2.locale.MessageManager" %>
<html>
<head>
    <title>Product Page</title>
</head>
<body>

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


<form method="post" action="logout">
    <button type="submit">Logout</button>
</form>
<%
    if(!isBlocked){
%>
<%-- Добавляем форму для кнопки Cart --%>
<form method="get" action="cart">
    <button type="submit">Cart</button>
</form>
<% } %>
<%-- Проверяем, является ли пользователь администратором и добавляем форму для кнопки AdminPanel --%>
<% if ((Boolean) request.getSession().getAttribute("isadmin")) { %>
<form method="post" action="adminpanel">
    <button type="submit">AdminPanel</button>
</form>
<% } %>
<%
    if(!isBlocked){
%>
<h2>Products</h2>

<div>
    <!-- Пример товара -->
    <div>
        <h3>Product 1</h3>
        <p>Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        <p>Price: $19.99</p>
        <button>Add to Cart</button>
    </div>

    <!-- Пример товара -->
    <div>
        <h3>Product 2</h3>
        <p>Description: Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.</p>
        <p>Price: $29.99</p>
        <button>Add to Cart</button>
    </div>

    <!-- Другие товары магазина могут быть добавлены здесь -->
</div>


</body>
</html>
<%
    }
%>