<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="bsuir.dtalalaev.lab2.locale.MessageManager" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h2>Registration Form</h2>

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

<!-- Форма регистрации -->
<form action="register" method="post">
    <!-- Логин -->
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <br>

    <!-- Имя пользователя -->
    <label for="fullName">Full Name:</label>
    <input type="text" id="fullName" name="fullName" required>
    <br>

    <!-- Пароль -->
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <br>

    <!-- Подтверждение пароля -->
    <label for="confirmPassword">Confirm Password:</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required>
    <br>

    <!-- Кнопка "Зарегистрироваться" -->
    <input type="submit" value="Register" >
</form>
</body>
</html>
