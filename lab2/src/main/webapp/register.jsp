<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bsuir.dtalalaev.lab2.locale.MessageManager" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f2f2f2; /* Светло-серый фон */
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        form {
            background-color: #fff; /* Белый фон для формы */
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333; /* Тёмный серый текст для заголовка */
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333; /* Тёмный серый текст для меток */
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            box-sizing: border-box;
            border: 1px solid #ccc; /* Светло-серая рамка для полей ввода */
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #4CAF50; /* Зелёная кнопка "Зарегистрироваться" */
            color: #fff; /* Белый текст на кнопке */
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049; /* Тёмно-зелёный цвет при наведении на кнопку */
        }

        p {
            text-align: center;
            margin-top: 10px;
        }

        a {
            color: #4CAF50; /* Зелёный цвет для ссылки */
            text-decoration: none;
        }

        a:hover {
            color: #45a049; /* Тёмно-зелёный цвет при наведении на ссылку */
        }
    </style>
</head>
<body>
<div>
    <h2>Registration Form</h2>

    <c:choose>
        <c:when test="${not empty requestScope.message}">
            ${requestScope.message.name}
            <br>
            ${requestScope.message.description}
            <br>
        </c:when>
        <c:otherwise>
            <%-- Ничего не делаем --%>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${not empty requestScope.exception}">
            ${requestScope.exception.name}
            <br>
            ${requestScope.exception.description}
            <br>
        </c:when>
        <c:otherwise>
            <%-- Ничего не делаем --%>
        </c:otherwise>
    </c:choose>

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

    <!-- Ссылка на страницу входа -->
    <p>
        Already have an account? <a href="login.jsp">Login</a>
    </p>
</div>
</body>
</html>
