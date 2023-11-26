package bsuir.dtalalaev.lab2;

import bsuir.dtalalaev.lab2.dbcontrollers.DataBase;
import bsuir.dtalalaev.lab2.entities.Cart;
import bsuir.dtalalaev.lab2.entities.Product;
import bsuir.dtalalaev.lab2.entities.User;
import bsuir.dtalalaev.lab2.locale.LanguageFabric;
import bsuir.dtalalaev.lab2.locale.MessageManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static void handleAdminAction(HelloServlet helloServlet, HttpServletResponse resp, HttpServletRequest req, String uri, String lastPathSegment) throws SQLException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (!DataBase.isAdmin(Integer.parseInt((String) req.getSession().getAttribute("userid")))) {
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
        if (DataBase.isAdmin(adminId)) {
            AdminController.loadAdminPanel(helloServlet, req, resp);
        } else {
            AccountManager.logout(helloServlet, req, resp);
        }
    }

    public static void loadProductsPage(HelloServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Product> productList = DataBase.getAllProducts();
            req.getSession().setAttribute("products", productList);
            servlet.getServletContext().getRequestDispatcher("/products.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getSession().setAttribute(MessageManager.EXCEPTION, MessageManager.getGetProductListException(LanguageFabric.parseLanguage(CookieManager.getLangFromCookie(req))));
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addProduct(HelloServlet helloServlet, HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        int productCount = Integer.parseInt(request.getParameter("productCount"));

        Part filePart = request.getPart("productImage");
        InputStream fileContent = filePart.getInputStream();

        // Отключаем автоматичесное закрытие InputStream после копирования
        // (чтобы избежать "java.io.IOException: Stream Closed")
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = fileContent.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            byte[] imageBytes = buffer.toByteArray();

            Product newProduct = new Product(0, productName, productDescription, productPrice, imageBytes, productCount);
            DataBase.addProduct(newProduct);
            AdminController.loadProductsPage(helloServlet, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteProduct(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        DataBase.deleteProduct(Integer.parseInt(req.getParameter("productId")));
        AdminController.loadProductsPage(helloServlet, req, resp);
    }

    public static void loadAdminCartPage(HelloServlet servlet, HttpServletResponse resp, HttpServletRequest req, int userId) {
        try {
            List<Cart> usercart = DataBase.getCartItemsByUserId(userId);
            req.getSession().setAttribute("usercart", usercart);
            req.getSession().setAttribute("username", DataBase.getLoginByUserId(userId));
            servlet.getServletContext().getRequestDispatcher("/admincart.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getSession().setAttribute(MessageManager.EXCEPTION, MessageManager.getGetProductListException(LanguageFabric.parseLanguage(CookieManager.getLangFromCookie(req))));
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCart(HelloServlet servlet, HttpServletResponse resp, HttpServletRequest req, String uri) throws SQLException {
        int cartId = Integer.parseInt(uri.split("/")[uri.split("/").length - 1]);
        int userId = DataBase.getUserIdByCartId(cartId);
        DataBase.deleteCartByCartId(cartId);
        AdminController.loadAdminCartPage(servlet, resp, req, userId);
    }
}
