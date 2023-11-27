package bsuir.dtalalaev.lab2;

import bsuir.dtalalaev.lab2.dbcontrollers.DataBase;
import bsuir.dtalalaev.lab2.entities.Cart;
import bsuir.dtalalaev.lab2.entities.Product;
import bsuir.dtalalaev.lab2.locale.LanguageFabric;
import bsuir.dtalalaev.lab2.locale.MessageManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
class for manipulate USER interface and actions
 */
public class AccountManager {
    private static final Logger logger = LogManager.getLogger(AccountManager.class);

    /**
     * create new user + redirect with message of handeling
     * @param servlet
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
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
                logger.info("User registered " + username + " " + fullName);
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

    /**
     * function for login, redirect with result message of handeling
     * @param servlet
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
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
            boolean isAdmin = false;
            int userid = DataBase.getUserIdByLoginAndPassword(username, password);
            if(DataBase.isAdmin(userid)){
                req.getSession().setAttribute("isadmin", Boolean.TRUE);
                isAdmin = true;
            } else {
                req.getSession().setAttribute("isadmin", Boolean.FALSE);
            }
            if(DataBase.isBlocked(userid)){
                req.getSession().setAttribute("isblocked", Boolean.TRUE);
            } else {
                req.getSession().setAttribute("isblocked", Boolean.FALSE);
            }
            req.getSession().setAttribute("userid", String.valueOf(userid));
            logger.info("User logged in " + username + (isAdmin?" ADMIN":""));
            AccountManager.loadShop(servlet, resp, req);
        } else {
            req.setAttribute(MessageManager.EXCEPTION, MessageManager.getIncorrectLoginOrPasswordMessage(
                    LanguageFabric.parseLanguage(
                            CookieManager.getLangFromCookie(req))
            ));
            servlet.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    /**
     * function to load user cart page
     * @param servlet
     * @param req
     * @param resp
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     */
    public static void loadUserCartPage(HelloServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        int userId = Integer.parseInt((String)req.getSession().getAttribute("userid"));
        List<Cart> userCart = DataBase.getCartItemsByUserId(userId);
        req.getSession().setAttribute("cartList", userCart);
        servlet.getServletContext().getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    /**
     * function to load shop page
     * @param servlet
     * @param resp
     * @param req
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    public static void loadShop(HelloServlet servlet, HttpServletResponse resp, HttpServletRequest req) throws SQLException, ServletException, IOException {
        List<Product> products = DataBase.getAllProducts();
        req.getSession().setAttribute("products", products);
        int userId = Integer.parseInt((String)req.getSession().getAttribute("userid"));
        req.getSession().setAttribute("isadmin", DataBase.isAdmin(userId));
        req.getSession().setAttribute("isblocked", DataBase.isBlocked(userId));
        servlet.getServletContext().getRequestDispatcher("/shop.jsp").forward(req, resp);
    }

    /**
     * function to log user out
     * @param helloServlet
     * @param req
     * @param resp
     * @throws IOException
     */
    public static void logout(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int userId = Integer.parseInt((String) req.getSession().getAttribute("userid"));
        req.getSession().setAttribute("userid", null);
        logger.info("User logged out " + DataBase.getLoginByUserId(userId));
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    /**
     * function to crate new row in DB with a pronuct in the user's cart
     * @param helloServlet
     * @param req
     * @param resp
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    public static void addProductToUsersCart(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String userId = req.getParameter("userId");
        String productId = req.getParameter("productId");
        String quantity = req.getParameter("quantity");

        int userIdInt = Integer.parseInt(userId);
        int productIdInt = Integer.parseInt(productId);
        int quantityInt = Integer.parseInt(quantity);

        DataBase.addCartItem(userIdInt, productIdInt, quantityInt);
        loadShop(helloServlet, resp, req);

    }

    /**
     * function to handle changing of product count in the user's cart
     * @param helloServlet
     * @param req
     * @param resp
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    public static void updateProductCol(HelloServlet helloServlet, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String userId = (String) req.getSession().getAttribute("userid");
        String cartId = req.getParameter("cartId");
        String quantity = req.getParameter("newCount");

        int userIdInt = Integer.parseInt(userId);
        int cartIdInt = Integer.parseInt(cartId);
        int quantityInt = Integer.parseInt(quantity);

        DataBase.updateUserCartCol(userIdInt, cartIdInt, quantityInt);
        AccountManager.loadUserCartPage(helloServlet, req, resp);
    }

    /**
     * function to delete one order from users cart
     * @param helloServlet
     * @param resp
     * @param req
     * @param uri
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    public static void deleteCart(HelloServlet helloServlet, HttpServletResponse resp, HttpServletRequest req, String uri) throws SQLException, ServletException, IOException {
        int cartId = Integer.parseInt(uri.split("/")[uri.split("/").length - 1]);
        DataBase.deleteCartByCartId(cartId);
        AccountManager.loadUserCartPage(helloServlet, req, resp);
    }
}
