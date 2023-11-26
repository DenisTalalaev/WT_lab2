package bsuir.dtalalaev.lab2.dbcontrollers;

import bsuir.dtalalaev.lab2.entities.Product;
import bsuir.dtalalaev.lab2.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM product";
    private static final String ADD_USER_QUERY = "INSERT INTO user (u_name, u_login, u_pass_hash, u_is_admin, u_is_blocked) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE u_id = ?";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO product (p_name, p_description, p_price, p_image, p_count) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM product WHERE p_id = ?";
    private static final String UPDATE_USER_STATUS_QUERY = "UPDATE user SET u_is_admin = ?, u_is_blocked = ? WHERE u_id = ?";
    private static final String ADD_BUCKET_QUERY = "INSERT INTO bucket (p_id, p_col, p_adding_date) VALUES (?, ?, ?)";
    private static final String DELETE_BUCKET_QUERY = "DELETE FROM bucket WHERE b_id = ?";
    private static final String PRINT_TABLE_DATA_QUERY = "SELECT * FROM %s";
    private static final String CHECK_LOGIN_AND_PASSWORD_QUERY = "SELECT * FROM user WHERE u_login = ? AND u_pass_hash = ?";
    private static final String IS_ADMIN_QUERY = "SELECT u_is_admin FROM user WHERE u_id = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM user";
    private static final String IS_BLOCKED_QUERY = "SELECT u_is_blocked FROM user WHERE u_id = ?";
    private static final String GET_USER_ID_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT u_id FROM user WHERE u_login = ? AND u_pass_hash = ?";

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


    public static void addUser(String userName, String login, String password) throws SQLException {
        addUser(userName, login, password, false, false);
    }

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
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }



    public static void deleteUser(int userId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }
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
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }


    public static void deleteProduct(int productId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
                preparedStatement.setInt(1, productId);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

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

    public static void addBucket(int productId, int productCol, int productAddingDate) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_BUCKET_QUERY)) {
                preparedStatement.setInt(1, productId);
                preparedStatement.setInt(2, productCol);
                preparedStatement.setInt(3, productAddingDate);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    public static void deleteBucket(int bucketId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BUCKET_QUERY)) {
                preparedStatement.setInt(1, bucketId);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

    public static void printAllData() throws SQLException {
        printTableData("user");
        printTableData("product");
        // Добавьте другие таблицы по мере необходимости
    }

    public static boolean checkLoginAndPassword(String login, String password) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN_AND_PASSWORD_QUERY)) {
                preparedStatement.setString(1, login);
                preparedStatement.setInt(2, password.hashCode());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    System.out.println("Login succeeded");
                    return resultSet.next();
                }
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
            System.out.println("Login error");
        }
    }

    private static void printTableData(String tableName) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String query = String.format(PRINT_TABLE_DATA_QUERY, tableName);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                System.out.println("Data from table " + tableName + ":");
                while (resultSet.next()) {
                    System.out.println("Row: " + resultSet.getInt(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3));
                }
                System.out.println();
            }
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }

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

    public static void banUser(int userId) throws SQLException {
        updateUserStatus(userId, DataBase.isAdmin(userId), true);
    }

    public static void unbanUser(int userId) throws SQLException {
        updateUserStatus(userId, DataBase.isAdmin(userId), false);
    }

    public static void promoteAdmin(int userId) throws SQLException {
        updateUserStatus(userId, true, DataBase.isBlocked(userId));
    }

    public static void demoteAdmin(int userId) throws SQLException {
        updateUserStatus(userId, false, DataBase.isBlocked(userId));
    }
}