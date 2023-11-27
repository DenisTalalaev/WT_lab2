package bsuir.dtalalaev.lab2.dbcontrollers;

import bsuir.dtalalaev.lab2.entities.Cart;
import bsuir.dtalalaev.lab2.entities.Product;
import bsuir.dtalalaev.lab2.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for connect and user database
 */
public class DataBase {
    private static final Logger logger = LogManager.getLogger(DataBase.class);

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM product";
    private static final String ADD_USER_QUERY = "INSERT INTO user (u_name, u_login, u_pass_hash, u_is_admin, u_is_blocked) VALUES (?, ?, ?, ?, ?)";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO product (p_name, p_description, p_price, p_image, p_count) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM product WHERE p_id = ?";
    private static final String UPDATE_USER_STATUS_QUERY = "UPDATE user SET u_is_admin = ?, u_is_blocked = ? WHERE u_id = ?";
    private static final String CHECK_LOGIN_AND_PASSWORD_QUERY = "SELECT * FROM user WHERE u_login = ? AND u_pass_hash = ?";
    private static final String IS_ADMIN_QUERY = "SELECT u_is_admin FROM user WHERE u_id = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM user";
    private static final String IS_BLOCKED_QUERY = "SELECT u_is_blocked FROM user WHERE u_id = ?";
    private static final String GET_USER_ID_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT u_id FROM user WHERE u_login = ? AND u_pass_hash = ?";
    private static final String GET_CART_ITEMS_BY_USER_ID_QUERY = "SELECT * FROM cart WHERE u_id = ?";
    private static final String GET_LOGIN_BY_USER_ID_QUERY = "SELECT u_login FROM user WHERE u_id = ?";
    private static final String GET_USER_ID_BY_CART_ID_QUERY = "SELECT u_id FROM cart WHERE c_id = ?";
    private static final String DELETE_CART_BY_CART_ID_QUERY = "DELETE FROM cart WHERE c_id = ?";
    private static final String ADD_CART_QUERY = "INSERT INTO cart (u_id, p_id, p_col) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_COL_QUERY = "UPDATE cart SET p_col = ? WHERE u_id = ? AND c_id = ?";
    private static final String DELETE_FROM_CART_QUERY = "DELETE FROM cart WHERE p_id = ?\n";


    /**
     * Method to delete cart by it id
     * @param cartId
     * @throws SQLException
     */
    public static void deleteCartByCartId(int cartId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CART_BY_CART_ID_QUERY)) {
                preparedStatement.setInt(1, cartId);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    /**
     * Find user by it's cart
     * @param cartId
     * @return
     * @throws SQLException
     */
    public static int getUserIdByCartId(int cartId) throws SQLException {
        int userId = -1;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID_BY_CART_ID_QUERY)) {
                preparedStatement.setInt(1, cartId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("u_id");
                    }
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return userId;
    }

    /**
     * Add new cart for user. cart has a product and count of froduct
     * @param userId
     * @param productId
     * @param count
     * @throws SQLException
     */
    public static void addCartItem(int userId, int productId, int count) throws SQLException {
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();


            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_CART_QUERY)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, productId);
                preparedStatement.setInt(3, count);

                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    /**
     * Take all carts by userId
     * @param userId
     * @return
     * @throws SQLException
     */
    public static List<Cart> getCartItemsByUserId(int userId) throws SQLException {
        List<Cart> cartList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CART_ITEMS_BY_USER_ID_QUERY)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int cartId = resultSet.getInt("c_id");
                        int productId = resultSet.getInt("p_id");
                        int count = resultSet.getInt("p_col");

                        Cart cartItem = new Cart(cartId, userId, productId, count);
                        cartList.add(cartItem);
                    }
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return cartList;
    }

    /**
     * Get all products rom the catalog
     * @return
     * @throws SQLException
     */
    public static List<Product> getAllProducts() throws SQLException {
        List<Product> productList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_QUERY);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int productId = resultSet.getInt("p_id");
                    String productName = resultSet.getString("p_name");
                    String productDescription = resultSet.getString("p_description");
                    double productPrice = resultSet.getDouble("p_price");
                    byte[] productImage = resultSet.getBytes("p_image");
                    int productCount = resultSet.getInt("p_count");

                    Product product = new Product(productId, productName, productDescription,
                            productPrice, productImage, productCount);
                    productList.add(product);
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return productList;
    }

    /**
     * add user by login, name, password. no admin, no block
     * @param userName
     * @param login
     * @param password
     * @throws SQLException
     */
    public static void addUser(String userName, String login, String password) throws SQLException {
        addUser(userName, login, password, false, false);
    }

    /**
     * Add new user with access paramentes: ban and admin
     * @param userName
     * @param login
     * @param password
     * @param isAdmin
     * @param isBlocked
     * @throws SQLException
     */
    public static void addUser(String userName, String login, String password, boolean isAdmin, boolean isBlocked) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY)) {
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, login);
                preparedStatement.setInt(3, password.hashCode());
                preparedStatement.setBoolean(4, isAdmin);
                preparedStatement.setBoolean(5, isBlocked);

                preparedStatement.executeUpdate();
            }
            logger.info("New user added");
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    /**
     * Method to add a product to the DB
     * @param product
     * @throws SQLException
     */
    public static void addProduct(Product product) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_QUERY)) {
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setString(2, product.getProductDescription());
                preparedStatement.setDouble(3, product.getProductPrice());
                preparedStatement.setBytes(4, product.getProductImage());
                preparedStatement.setInt(5, product.getProductCount());

                preparedStatement.executeUpdate();
            }
            logger.info("New product added: " + product);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    /**
     * Method to delete product by it's id
     * @param productId
     * @throws SQLException
     */
    public static void deleteProduct(int productId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
                // Удаляем продукт из таблицы "product"
                preparedStatement.setInt(1, productId);
                preparedStatement.executeUpdate();

                // Удаляем все связанные записи из таблицы "cart"
                try (PreparedStatement deleteFromCart = connection.prepareStatement(DELETE_FROM_CART_QUERY)) {
                    deleteFromCart.setInt(1, productId);
                    deleteFromCart.executeUpdate();
                }
            }
            logger.info("Product with id " + productId + " deleted");
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    /**
     * Method to undate block/isadmin sttus for user
     * @param userId
     * @param isAdmin
     * @param isBlocked
     * @throws SQLException
     */
    public static void updateUserStatus(int userId, boolean isAdmin, boolean isBlocked) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_STATUS_QUERY)) {
                preparedStatement.setBoolean(1, isAdmin);
                preparedStatement.setBoolean(2, isBlocked);
                preparedStatement.setInt(3, userId);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    /**
     * Method to authorize user by its login and password
     * @param login
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean checkLoginAndPassword(String login, String password) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN_AND_PASSWORD_QUERY)) {
                preparedStatement.setString(1, login);
                preparedStatement.setInt(2, password.hashCode());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    /**
     * returns true if user with UserID is admin
     * @param userId
     * @return
     * @throws SQLException
     */
    public static boolean isAdmin(int userId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(IS_ADMIN_QUERY)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean("u_is_admin");
                    }
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return false;
    }

    /**
     * return list of all users in system
     * @return
     * @throws SQLException
     */
    public static List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_QUERY);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt("u_id");
                    String userName = resultSet.getString("u_name");
                    String userLogin = resultSet.getString("u_login");
                    boolean isAdmin = resultSet.getBoolean("u_is_admin");
                    boolean isBlocked = resultSet.getBoolean("u_is_blocked");

                    User user = new User(userId, userName, userLogin, isAdmin, isBlocked);
                    userList.add(user);
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return userList;
    }

    /**
     * Method to check is User with UserId is blocked
     * @param userId
     * @return
     * @throws SQLException
     */
    public static boolean isBlocked(int userId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(IS_BLOCKED_QUERY)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean("u_is_blocked");
                    }
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return false;
    }

    /**
     * Method to take a unserId by it's login and password
     * @param login
     * @param password
     * @return
     * @throws SQLException
     */
    public static int getUserIdByLoginAndPassword(String login, String password) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID_BY_LOGIN_AND_PASSWORD_QUERY)) {
                preparedStatement.setString(1, login);
                preparedStatement.setInt(2, password.hashCode());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("u_id");
                    }
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return -1;
    }

    /**
     * Function to gae user login by userId
     * @param userId
     * @return
     * @throws SQLException
     */
    public static String getLoginByUserId(int userId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOGIN_BY_USER_ID_QUERY)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("u_login");
                    }
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        return null; // или выберите другое значение по умолчанию, в зависимости от ваших требований
    }

    /**
     * Function to ser staUS Blocked for user with UserID
     * @param userId
     * @throws SQLException
     */
    public static void banUser(int userId) throws SQLException {
        updateUserStatus(userId, DataBase.isAdmin(userId), true);
        logger.info("user " + DataBase.getLoginByUserId(userId) + " baned");
    }

    /**
     * Function to ser staus UNblocked for user with UserID
     * @param userId
     * @throws SQLException
     */
    public static void unbanUser(int userId) throws SQLException {
        updateUserStatus(userId, DataBase.isAdmin(userId), false);
        logger.info("user " + DataBase.getLoginByUserId(userId) + " unbaned");
    }

    /**
     * Function to ser staUS isAdmin = true for user with UserID
     * @param userId
     * @throws SQLException
     */
    public static void promoteAdmin(int userId) throws SQLException {
        updateUserStatus(userId, true, DataBase.isBlocked(userId));
        logger.info("user " + DataBase.getLoginByUserId(userId) + " now is Admin");
    }

    /**
     *  Function to ser staUS isAdmin = FALSE for user with UserID
     * @param userId
     * @throws SQLException
     */
    public static void demoteAdmin(int userId) throws SQLException {
        updateUserStatus(userId, false, DataBase.isBlocked(userId));
        logger.info("user " + DataBase.getLoginByUserId(userId) + " is not Admin now");
    }

    /**
     * Function to get a product (Product entity) by productId
     * @param productId
     * @return
     */
    public static Product getProductById(int productId) {
        Product product = null;
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            String query = "SELECT * FROM product WHERE p_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String productName = resultSet.getString("p_name");
                        String productDescription = resultSet.getString("p_description");
                        double productPrice = resultSet.getDouble("p_price");
                        byte[] productImage = resultSet.getBytes("p_image");
                        int productCount = resultSet.getInt("p_count");

                        product = new Product(productId, productName, productDescription, productPrice, productImage, productCount);
                    }
                }
            }
        } catch (SQLException e) {
            // Обработка исключений, например, логирование ошибок
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    ConnectionPool.getInstance().releaseConnection(connection);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return product;
    }

    /**
     * function to update count of product in a User (userid) cart (cartId) to quantity
     * @param userId
     * @param cartId
     * @param quantity
     * @throws SQLException
     */
    public static void updateUserCartCol(int userId, int cartId, int quantity) throws SQLException {
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CART_COL_QUERY)) {
                preparedStatement.setInt(1, quantity);
                preparedStatement.setInt(2, userId);
                preparedStatement.setInt(3, cartId);

                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

}
