<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bsuir.dtalalaev.lab2.locale.MessageManager" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>

<h2>Login</h2>

<%
    MessageManager message = (MessageManager) request.getAttribute(MessageManager.MESSAGE);
    MessageManager exception = (MessageManager) request.getAttribute(MessageManager.EXCEPTION);
%>

<%-- Проверка на null перед выводом сообщений --%>
<% if (message != null) { %>
<%= message.getName()%>
<br>
<%= message.getDescription()%>
<br>
<% } %>

<%-- Проверка на null перед выводом исключения --%>
<% if (exception != null) { %>
<%= exception.getName()%>
<br>
<%= exception.getDescription()%>
<br>
<% } %>

<!-- Форма авторизации -->
<form action="login" method="post">
    <!-- Логин -->
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <br>

    <!-- Пароль -->
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <br>

    <!-- Кнопка "Войти" -->
    <input type="submit" value="Log in">
</form>

</body>
</html>
