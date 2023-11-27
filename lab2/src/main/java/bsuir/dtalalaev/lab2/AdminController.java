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


/**
class to handle admin actions and load admin pages
 */
public class AdminController {

    /**
     * function to load admin panel
     * @param servlet
     * @param req
     * @param resp
     */
    public static void loadAdminPanel(HelloServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        try {
            int userid = Integer.parseInt(((String) req.getSession().getAttribute("userid")));
            if(DataBase.isAdmin(userid)){
                req.getSession().setAttribute("isadmin", Boolean.TRUE);
            } else {
                req.getSession().setAttribute("isadmin", Boolean.FALSE);
            }
            List<User> userList = DataBase.getAllUsers();
            req.getSession().setAttribute("users", userList);
            servlet.getServletContext().getRequestDispatcher("/adminpanel.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getSession().setAttribute(MessageManager.EXCEPTION, MessageManager.getGetUserListException(LanguageFabric.parseLanguage(CookieManager.getLangFromCookie(req))));
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * function to handle admin action with accounts: ban/unban, promote/demote.
     * In case user demote himself, he will be redirect to the shop page
     * @param helloServlet
     * @param resp
     * @param req
     * @param uri
     * @param lastPathSegment
     * @throws SQLException
     * @throws IOException
     */
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
        req.getSession().setAttribute("isadmin", DataBase.isAdmin(adminId));
        AdminController.loadAdminPanel(helloServlet, req, resp);
    }

    /**
     * load page with all products and form to add/delete product
     * @param servlet
     * @param req
     * @param resp
     */
    public static void loadProductsPage(HelloServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        try {
            int userid = Integer.parseInt(((String) req.getSession().getAttribute("userid")));
            if(DataBase.isAdmin(userid)){
                req.getSession().setAttribute("isadmin", Boolean.TRUE);
            } else {
                req.getSession().setAttribute("isadmin", Boolean.FALSE);
            }
            List<Product> productList = DataBase.getAllProducts();
            req.getSession().setAttribute("products", productList);
            servlet.getServletContext().getRequestDispatcher("/products.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getSession().setAttribute(MessageManager.EXCEPTION, MessageManager.getGetProductListException(LanguageFabric.parseLanguage(CookieManager.getLangFromCookie(req))));
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * add new product to DB, data took from POST
     * @param helloServlet
     * @param request
     * @param response
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     */
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


    /**
     * function to delete product from database
     * @param helloServlet
     * @param req
     * @param resp
     * @throws IOException
     * @throws SQLException
     */
    public static void deleteProduct(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        DataBase.deleteProduct(Integer.parseInt(req.getParameter("productId")));
        AdminController.loadProductsPage(helloServlet, req, resp);
    }

    /**
     * laod admin cart page, shows all cart for any User in system
     * @param servlet
     * @param resp
     * @param req
     * @param userId
     */
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

    /**
     * delete some product from USERS cart
     * @param servlet
     * @param resp
     * @param req
     * @param uri
     * @throws SQLException
     */
    public static void deleteCart(HelloServlet servlet, HttpServletResponse resp, HttpServletRequest req, String uri) throws SQLException {
        int cartId = Integer.parseInt(uri.split("/")[uri.split("/").length - 1]);
        int userId = DataBase.getUserIdByCartId(cartId);
        DataBase.deleteCartByCartId(cartId);
        AdminController.loadAdminCartPage(servlet, resp, req, userId);
    }
}
