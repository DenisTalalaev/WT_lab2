<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bsuir.dtalalaev.lab2.locale.MessageManager" %>
<html>
<head>
    <title>Log in</title>
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
            background-color: #4CAF50; /* Зелёная кнопка "Войти" */
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
    <h2>Login</h2>

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

    <!-- Ссылка на страницу регистрации -->
    <p style="text-align: center; margin-top: 10px;">
        Don't have an account? <a href="register.jsp">Register</a>
    </p>
</div>

</body>
</html>
