package com.tsarova.salon.pool;

import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.resource.db.DBParameter;
import com.tsarova.salon.resource.db.DBResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Veronika Tsarova
 */
public class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ReentrantLock lockingInstance = new ReentrantLock();
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);

    private BlockingQueue<Connection> connectionQueue;
    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private static volatile ConnectionPool connectionPoolInstance = null;

    public static ConnectionPool getInstance() {
        if (connectionPoolInstance == null) {
            lockingInstance.lock();
            try {
                if (instanceCreated.compareAndSet(false, true)) {
                    connectionPoolInstance = new ConnectionPool();
                }
            } finally {
                lockingInstance.unlock();
            }
        }
        return connectionPoolInstance;
    }

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager
                    .getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            this.poolSize = 20;
        }
        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Error initializing connection pool", e);
        }
    }

    private void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user,
                        password);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                connectionQueue.add(proxyConnection);
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.ERROR, "Can't init pool data");
            throw new ConnectionPoolException(e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Can't take connection from the pool");
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    void returnConnection(Connection connection) {
        Connection proxyConnection;
        try {
            proxyConnection = new ProxyConnection(connection);
            connectionQueue.put(proxyConnection);
        } catch (InterruptedException | SQLException e) {
            logger.log(Level.ERROR, "Can't return connection to the pool");
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Can't return connection to the pool");
            }
        }
    }
}