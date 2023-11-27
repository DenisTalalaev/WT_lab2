package bsuir.dtalalaev.lab2.dbcontrollers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to manage all connections. Create a predicted count of values
 */


public class ConnectionPool {
    /**Data fpr connection
     *
     */

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final String userName = "elcherry_wt_2";
    private static final String password = "VHYI&tyw6d7ITUWGj&&";
    private static final String url = "jdbc:mysql://elcherry.beget.tech/elcherry_wt_2?autoReconnect=true";

    /**
     * max count of pool
     */
    private static final int INITIAL_POOL_SIZE = 5;
    /**pool containers
     *
     */
    private final LinkedList<Connection> connectionPool;
    private final LinkedList<Connection> usedConnections = new LinkedList<>();

    private ConnectionPool() throws SQLException {
        logger.info("Initialised connection pool");
        connectionPool = new LinkedList<>();
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }

    /**
     * function to create new connection (autorun if some connections closed)
     * @return
     * @throws SQLException
     */
    private Connection createConnection() throws SQLException {
        logger.info("add new connection to pool");
        return DriverManager.getConnection(url, userName, password);
    }

    /**
     * function to get connection from pool
     * @return
     * @throws SQLException
     */
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

    /**
     * return connection to pool
     * @param connection
     */
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

    /**
     * get connetion from out the class
     * @return
     * @throws SQLException
     */
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
