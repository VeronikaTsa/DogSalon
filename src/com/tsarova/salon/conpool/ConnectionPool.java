package com.tsarova.salon.conpool;

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
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger(com.tsarova.salon.pool.ConnectionPool.class);
    private static ReentrantLock lock = new ReentrantLock();

    private BlockingQueue<ProxyConnection> connectionQueue;
    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private static volatile ConnectionPool conPool = null;

    public static ConnectionPool getInstance() {
        if (conPool == null) {
            try {
                lock.lock();
                if (conPool == null) {                                          //?????????????????????????
                    conPool = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return conPool;
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

    public void initPoolData() throws ConnectionPoolException {
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
            throw new ConnectionPoolException();
        }
    }

    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new ConnectionPoolException();
        }
        return connection;
    }

    public void returnConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool");
        }
    }


}
