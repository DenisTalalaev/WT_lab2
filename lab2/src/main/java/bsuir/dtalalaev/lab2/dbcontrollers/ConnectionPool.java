package bsuir.dtalalaev.lab2.dbcontrollers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {

    private static final String userName = "elcherry_wt_2";
    private static final String password = "VHYI&tyw6d7ITUWGj&&";
    private static final String url = "jdbc:mysql://elcherry.beget.tech/elcherry_wt_2?autoReconnect=true";


    private static final int INITIAL_POOL_SIZE = 5;

    private final LinkedList<Connection> connectionPool;
    private final LinkedList<Connection> usedConnections = new LinkedList<>();

    private ConnectionPool() throws SQLException {
        connectionPool = new LinkedList<>();
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < INITIAL_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new SQLException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.removeFirst();

        // Проверка валидности соединения перед выдачей
        if (!connection.isValid(5)) { // 5 секунд таймаут для проверки валидности
            // Если соединение невалидное, создайте новое
            connection = createConnection();
        }

        usedConnections.addLast(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        connectionPool.addLast(connection);
        usedConnections.remove(connection);
    }

    public void closeAllConnections() throws SQLException {
        for (Connection connection : connectionPool) {
            connection.close();
        }
        for (Connection connection : usedConnections) {
            connection.close();
        }
    }

    public static ConnectionPool getInstance() throws SQLException {
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        private static final ConnectionPool INSTANCE;

        static {
            try {
                INSTANCE = new ConnectionPool();
            } catch (SQLException e) {
                throw new RuntimeException("Error initializing connection pool!", e);
            }
        }
    }
}
