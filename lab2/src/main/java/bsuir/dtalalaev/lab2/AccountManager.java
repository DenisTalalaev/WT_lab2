package bsuir.dtalalaev.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AccountManager {
    public synchronized static void register(HelloServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Установка кодировки для корректного чтения данных из POST-запроса
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // Чтение данных из POST-запроса
        String username = req.getParameter("username");
        String fullName = req.getParameter("fullName");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        if(password.equals(confirmPassword)){
            try{
                DataBase.addUser(fullName, username, password);
                req.setAttribute(MessageManager.MESSAGE, MessageManager.getRegistrationCompletedMessage(
                        LanguageFabric.parseLanguage(
                                CookieManager.getLangFromCookie(req))
                ));
                servlet.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);

            } catch (SQLIntegrityConstraintViolationException e){
                req.setAttribute(MessageManager.EXCEPTION, MessageManager.getLoginOccupiedException(
                        LanguageFabric.parseLanguage(
                                CookieManager.getLangFromCookie(req))
                ));
                servlet.getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
            } catch (SQLException p){
                req.setAttribute(MessageManager.EXCEPTION, MessageManager.getConnectionException(
                        LanguageFabric.parseLanguage(
                                CookieManager.getLangFromCookie(req))
                ));
                servlet.getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute(MessageManager.MESSAGE, MessageManager.getPasswordsAreNotMatchException(
                    LanguageFabric.parseLanguage(
                            CookieManager.getLangFromCookie(req))
            ));
            servlet.getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }

    }

    public synchronized static void login(HelloServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // Чтение данных из POST-запроса
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(DataBase.checkLoginAndPassword(username, password)) {
            req.setAttribute(MessageManager.EXCEPTION, MessageManager.getWelcomeMessage(
                    LanguageFabric.parseLanguage(
                            CookieManager.getLangFromCookie(req))
            ));
            int userid = DataBase.getUserIdByLoginAndPassword(username, password);
            if(DataBase.isAdmin(userid)){
                req.getSession().setAttribute("isadmin", Boolean.TRUE);
            } else {
                req.getSession().setAttribute("isadmin", Boolean.FALSE);
            }
            if(DataBase.isBlocked(userid)){
                req.getSession().setAttribute("isblocked", Boolean.TRUE);
            } else {
                req.getSession().setAttribute("isblocked", Boolean.FALSE);
            }
            req.getSession().setAttribute("userid", String.valueOf(userid));
            resp.sendRedirect("shop.jsp");
        } else {
            req.setAttribute(MessageManager.EXCEPTION, MessageManager.getIncorrectLoginOrPasswordMessage(
                    LanguageFabric.parseLanguage(
                            CookieManager.getLangFromCookie(req))
            ));
            servlet.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    public static void logout(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("userid", null);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

}
