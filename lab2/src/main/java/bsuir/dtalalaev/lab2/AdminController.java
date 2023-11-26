package bsuir.dtalalaev.lab2;

import bsuir.dtalalaev.lab2.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    public static void loadAdminPanel(HelloServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<User> userList = DataBase.getAllUsers();
            req.getSession().setAttribute("users", userList);
            servlet.getServletContext().getRequestDispatcher("/adminpanel.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getSession().setAttribute(MessageManager.EXCEPTION, MessageManager.getGetUserListException(LanguageFabric.parseLanguage(CookieManager.getLangFromCookie(req))));
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void blockUser(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException, SQLException {
        if(DataBase.isBlocked(userId)){
            DataBase.unbanUser(userId);
            System.out.println("Unban");
        } else{
            System.out.println("ban");
            DataBase.banUser(userId);
        }
    }

    public static void promoteUser(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException, SQLException {
        if(DataBase.isAdmin(userId)){
            System.out.println("demote");
            DataBase.demoteAdmin(userId);
        } else {
            DataBase.promoteAdmin(userId);
            System.out.println("promote");
        }
    }

    public static void handleAdminAction(HelloServlet helloServlet, HttpServletResponse resp, HttpServletRequest req, String uri, String lastPathSegment) throws SQLException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if(!DataBase.isAdmin(Integer.parseInt((String)req.getSession().getAttribute("userid")))){
            AccountManager.logout(helloServlet, req, resp);
            return;
        }
        if (pathParts.length > 1) {
            int userId = Integer.parseInt(pathParts[1]);
            String[] actions = req.getParameterValues("action");
            String action = (actions != null && actions.length > 0) ? actions[0] : null;
            System.out.println(action);
            if (action != null && !action.isEmpty()) {
                switch (action.toLowerCase()) {
                    case "block":
                        // Обработка блокировки/разблокировки пользователя
                        try {
                            DataBase.banUser(userId);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "unblock":
                        // Обработка блокировки/разблокировки пользователя
                        try {
                            DataBase.unbanUser(userId);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "promote":
                        try {
                            DataBase.promoteAdmin(userId);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "demote":
                        // Обработка назначения/разжалования администратора
                        try {
                            DataBase.demoteAdmin(userId);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            }
        }
        int adminId = Integer.parseInt(((String) req.getSession().getAttribute("userid")));
        if(DataBase.isAdmin(adminId)){
            AdminController.loadAdminPanel(helloServlet, req, resp);
        } else {
            AccountManager.logout(helloServlet, req, resp);
        }
    }
}
